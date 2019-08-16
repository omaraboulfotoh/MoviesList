package com.dev.movieslist.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.dev.movieslist.widgets.OnItemClickListener


open class BaseRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var clickableRootView: View? = null
    fun setOnItemClickListener(itemClickListener: OnItemClickListener?) {
        if (clickableRootView != null) {
            clickableRootView!!.setOnClickListener { v ->
                itemClickListener?.onItemClick(v, adapterPosition)
            }
        } else {
            itemView.setOnClickListener { v ->
                itemClickListener?.onItemClick(v, adapterPosition)
            }
        }
    }

    fun setClickableRootView(clickableRootView: View) {
        this.clickableRootView = clickableRootView
    }

    fun findViewById(viewId: Int): View? {
        return itemView.findViewById(viewId)
    }
}
