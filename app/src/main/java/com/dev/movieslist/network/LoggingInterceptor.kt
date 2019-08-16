package com.dev.movieslist.network

import android.util.Log
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.internal.http.HttpHeaders
import okio.Buffer
import java.io.EOFException
import java.io.IOException
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit

class LoggingInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()

        val requestBody = request.body()
        val hasRequestBody = requestBody != null

        val connection = chain.connection()
        val protocol = if (connection != null) connection.protocol() else Protocol.HTTP_1_1
        val requestStartMessage = "--> " + request.method() + ' '.toString() + request.url() + ' '.toString() + protocol
        Log.i(TAG, requestStartMessage)

        if (hasRequestBody) {
            // Request body headers are only present when installed as a network interceptor. Force
            // them to be included (when available) so there values are known.
            if (requestBody!!.contentType() != null) {
                Log.i(TAG, "Content-Type: " + requestBody.contentType()!!)
            }
            if (requestBody.contentLength() != -1L) {
                Log.i(TAG, "Content-Length: " + requestBody.contentLength())
            }
        }

        val requestHeaders = request.headers()
        run {
            var i = 0
            val count = requestHeaders.size()
            while (i < count) {
                val name = requestHeaders.name(i)
                // Skip headers from the request body as they are explicitly logged above.
                if (!"Content-Type".equals(name, ignoreCase = true) && !"Content-Length".equals(name, ignoreCase = true)) {
                    Log.i(TAG, name + ": " + requestHeaders.value(i))
                }
                i++
            }
        }

        if (!hasRequestBody) {
            Log.i(TAG, "--> END " + request.method())
        } else if (bodyEncoded(request.headers())) {
            Log.i(TAG, "--> END " + request.method() + " (encoded body omitted)")
        } else {
            val buffer = Buffer()
            requestBody!!.writeTo(buffer)

            var charset: Charset? = UTF8
            val contentType = requestBody.contentType()
            if (contentType != null) {
                charset = contentType.charset(UTF8)
            }

            Log.i(TAG, "")
            if (isPlaintext(buffer)) {
                Log.i(TAG, buffer.readString(charset!!))
                Log.i(TAG, "--> END " + request.method() + " (" + requestBody.contentLength() + "-byte body)")
            } else {
                Log.i(TAG, "--> END " + request.method() + " (binary " + requestBody.contentLength() + "-byte body omitted)")
            }
        }

        val startNs = System.nanoTime()
        val response: Response
        try {
            response = chain.proceed(request)
        } catch (e: Exception) {
            Log.i(TAG, "<-- HTTP FAILED: $e")
            throw e
        }

        val tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)

        val responseBody = response.body()
        val contentLength = responseBody!!.contentLength()
        val bodySize = if (contentLength != -1L) contentLength.toString() + "-byte" else "unknown-length"
        Log.i(TAG, "<-- " + response.code() + ' '.toString() + response.message() + ' '.toString() + response.request().url() + " (" + tookMs + "ms" + "" + ')'.toString())

        val responseHeaders = response.headers()
        var i = 0
        val count = responseHeaders.size()
        while (i < count) {
            Log.i(TAG, responseHeaders.name(i) + ": " + responseHeaders.value(i))
            i++
        }

        if (!HttpHeaders.hasBody(response)) {
            Log.i(TAG, "<-- END HTTP")
        } else if (bodyEncoded(response.headers())) {
            Log.i(TAG, "<-- END HTTP (encoded body omitted)")
        } else {
            val source = responseBody.source()
            source.request(java.lang.Long.MAX_VALUE) // Buffer the entire body.
            val buffer = source.buffer()

            var charset: Charset? = UTF8
            val contentType = responseBody.contentType()
            if (contentType != null) {
                charset = contentType.charset(UTF8)
            }

            if (!isPlaintext(buffer)) {
                Log.i(TAG, "")
                Log.i(TAG, "<-- END HTTP (binary " + buffer.size + "-byte body omitted)")
                return response
            }

            if (contentLength != 0L) {
                Log.i(TAG, "")
                Log.i(TAG, buffer.clone().readString(charset!!))
            }

            Log.i(TAG, "<-- END HTTP (" + buffer.size + "-byte body)")
        }

        return response
    }

    private fun bodyEncoded(headers: Headers): Boolean {
        val contentEncoding = headers.get("Content-Encoding")
        return contentEncoding != null && !contentEncoding.equals("identity", ignoreCase = true)
    }

    companion object {

        private val UTF8 = Charset.forName("UTF-8")
        private val TAG = LoggingInterceptor::class.java.simpleName

        /**
         * Returns true if the body in question probably contains human readable text. Uses a small sample
         * of code points to detect unicode control characters commonly used in binary file signatures.
         */
        internal fun isPlaintext(buffer: Buffer): Boolean {
            try {
                val prefix = Buffer()
                val byteCount = if (buffer.size < 64) buffer.size else 64
                buffer.copyTo(prefix, 0, byteCount)
                for (i in 0..15) {
                    if (prefix.exhausted()) {
                        break
                    }
                    val codePoint = prefix.readUtf8CodePoint()
                    if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                        return false
                    }
                }
                return true
            } catch (e: EOFException) {
                return false // Truncated UTF-8 sequence.
            }

        }
    }
}
