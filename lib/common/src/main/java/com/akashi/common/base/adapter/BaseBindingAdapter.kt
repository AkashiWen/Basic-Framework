package com.akashi.common.base.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseBindingAdapter<E : Any, V : ViewBinding> :
    RecyclerView.Adapter<BaseBindingHolder<V>>() {
    /**
     * 数据源
     */
    private val _data: MutableList<E> = mutableListOf()

    val data get() = _data

    fun removeItem(position: Int) {
        if (position < 0 || position >= itemCount) {
            return
        }
        _data.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount - position)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(dataList: List<E>) {
        _data.clear()
        _data.addAll(dataList)
        notifyDataSetChanged()
    }

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseBindingHolder<V>

    override fun getItemCount(): Int = _data.size

    override fun onBindViewHolder(holder: BaseBindingHolder<V>, position: Int) {
        onBindViewHolder(
            holder,
            holder.adapterPosition,
            holder.binding,
            _data[holder.adapterPosition]
        )
    }

    abstract fun onBindViewHolder(holder: BaseBindingHolder<V>, position: Int, binding: V, bean: E)
}

open class BaseBindingHolder<V : ViewBinding>(val binding: V) :
    RecyclerView.ViewHolder(binding.root) {

}