package com.azharkova.newsapp

import com.azharkova.newsapp.network.NetworkClient
import com.azharkova.newsapp.service.NewsService
import kotlin.native.concurrent.ThreadLocal

class DI {
    @ThreadLocal
    companion object DI {
        val instance = DI()
    }

    val networkClient: NetworkClient by lazy {
        NetworkClient()
    }

    val newsService: NewsService by lazy {
        NewsService(networkClient)
    }
}