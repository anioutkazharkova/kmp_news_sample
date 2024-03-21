package com.azharkova.kmm_news

import com.azharkova.kmm_news.network.NetworkClient
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object DI {
    val networkClient: NetworkClient by lazy {
        NetworkClient()
    }

    val newsService: NewsService by lazy {
        NewsService(networkClient)
    }
}