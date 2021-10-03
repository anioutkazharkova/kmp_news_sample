package com.azharkova.newsapp.redux

import com.azharkova.news.data.NewsItem

data class NewsStoreState(
    val progress: Boolean,
    val news: List<NewsItem>,
    val selectedItem: NewsItem? = null //null means selected all
) : StoreState