package com.azharkova.news.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.azharkova.news.R
import com.azharkova.news.data.NewsItem
import com.azharkova.news.util.loadImage

class NewsItemHolder(inflater: LayoutInflater,
                              container: ViewGroup
) :BaseViewHolder<NewsItem>(inflater.inflate(R.layout.item_layout, container, false)) {

    private var image:ImageView? = null
    private var title: TextView? = null
    private  var date:TextView? = null

    init {
        image = itemView.findViewById<ImageView>(R.id.image_news)
        title = itemView.findViewById<TextView>(R.id.title_news)
        date = itemView.findViewById<TextView>(R.id.date_news)
    }

    override fun bindItem(item: NewsItem) {
        date?.text = item.publishedAt
        image?.loadImage(item.urlToImage ?: "")
        title?.text = item.title ?: ""
    }

}