package com.azharkova.kmm_news.presentation

import com.azharkova.kmm_news.DI
import com.azharkova.kmm_news.data.NewsList
import com.azharkova.kmm_news.threads.ioDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface IView {}

interface INewsListView : IView {
    fun setupNews(data: NewsList)
}

interface INewsListPresenter {
    fun loadNews()
}

class NewsListPresenter : BasePresenter(), INewsListPresenter {
    private val newsService = DI.newsService

    override fun loadNews() {
        scope.launch {
            val result = withContext(ioDispatcher) {
                newsService.loadNews()
            }
            result.getOrNull()?.let {
                (view as? INewsListView)?.setupNews(it)
            }
        }
    }
}