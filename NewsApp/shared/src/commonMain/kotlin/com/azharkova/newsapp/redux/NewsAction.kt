package com.azharkova.newsapp.redux

import com.azharkova.news.data.NewsItem
import com.azharkova.newsapp.service.ErrorResponse

sealed class NewsAction : Action {
    data class Refresh(val forceLoad: Boolean) : NewsAction()
    data class SelectItem(val feed: NewsItem?) : NewsAction()
    data class Data(val feeds: List<NewsItem>) : NewsAction()
    data class Error(val error: ErrorResponse) : NewsAction()
}