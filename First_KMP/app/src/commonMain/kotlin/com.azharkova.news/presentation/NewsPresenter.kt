package com.azharkova.news.presentation

import com.azharkova.news.data.NewsItem
import com.azharkova.news.dispatcher.defaultDispatcher
import com.azharkova.news.service.NewsApi
import com.azharkova.news.view.INewsListView
import kotlinx.coroutines.*

class NewsPresenter:BasePresenter<INewsListView>(defaultDispatcher){
    var service: NewsApi = NewsApi.shared
    var data: ArrayList<NewsItem> = arrayListOf()


    fun loadData() {
        //запускаем в скоупе
        scope.launch {
            service.getNewsList {
                val result = it
                if (result.errorResponse == null) {
                    data = arrayListOf()
                    data.addAll(result.content?.articles ?: arrayListOf())

                    view?.setupNews(data)
                }
            }
        }
    }
}