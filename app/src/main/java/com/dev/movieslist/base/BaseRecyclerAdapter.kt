package com.dev.movieslist.base

import androidx.recyclerview.widget.RecyclerView
import com.dev.movieslist.utils.helpers.OnItemClickListener
import java.util.*

/**
 * is a base class to extend from it the recyclerview adapter
 */
abstract class BaseRecyclerAdapter<Item>(var data: ArrayList<Item>) :
    RecyclerView.Adapter<BaseRecyclerViewHolder>() {
    var itemClickListener: OnItemClickListener? = null

    override fun getItemCount(): Int {
        return data.size
    }

    fun setOnItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    fun insertAll(position: Int, items: List<Item>) {
        data.addAll(position, items)
        notifyItemRangeInserted(position, items.size)
    }

    fun insert(position: Int, item: Item) {
        data.add(position, item)
        notifyItemInserted(position)
    }

    fun insert(item: Item) {
        data.add(item)
        notifyItemInserted(data.size - 1)
    }

    fun update(position: Int, item: Item) {
        data.removeAt(position)
        data.add(position, item)
        notifyItemChanged(position)
    }

    fun updateAll(items: List<Item>) {
        data.clear()
        data.addAll(items)
        notifyDataSetChanged()
    }

    fun replaceItem(position: Int, item: Item) {
        data.removeAt(position)
        data.add(position, item)
        notifyItemInserted(position)
    }


    fun remove(postion: Int) {
        data.removeAt(postion)
        notifyItemRemoved(postion)
    }

    fun clear() {
        data.clear()
        notifyDataSetChanged()
    }

}
