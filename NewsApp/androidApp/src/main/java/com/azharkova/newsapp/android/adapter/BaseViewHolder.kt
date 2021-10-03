package com.azharkova.news.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract  class BaseViewHolder<T> (itemView: View)
    : RecyclerView.ViewHolder(itemView) {

    abstract fun bindItem(item: T)

    open var tag: Int = 0
}