package com.azharkova.news.view

import com.azharkova.news.data.NewsItem

interface INewsListView :IView {
    fun setupNews(list: List<NewsItem>)
}

