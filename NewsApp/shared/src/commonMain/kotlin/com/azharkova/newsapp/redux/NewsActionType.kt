package com.azharkova.newsapp.redux

import com.azharkova.news.data.NewsItem
import com.azharkova.newsapp.service.ErrorResponse

interface NewsActionType {
    fun refresh(forceLoad: Boolean)
    fun selectItem(feed: NewsItem?)
    fun data(feeds: List<NewsItem>)
    fun error(error: ErrorResponse)
}