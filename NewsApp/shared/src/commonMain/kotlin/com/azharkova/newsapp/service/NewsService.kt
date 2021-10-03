package com.azharkova.newsapp.service

import com.azharkova.news.data.NewsItem
import com.azharkova.news.data.NewsList
import com.azharkova.newsapp.network.NetworkClient
import kotlin.native.concurrent.ThreadLocal

class NewsService constructor(val networkClient: NetworkClient) {

    suspend fun getNewsList():ContentResponse<NewsList>{
        return networkClient.getData(Urls.NEWS_LIST)
    }

    companion object Urls {
        const val NEWS_LIST = "v2/top-headlines?language=en"
    }
}