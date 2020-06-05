package com.azharkova.news.service

import com.azharkova.news.http.INetworkService
import com.azharkova.news.http.NetworkService
import kotlin.native.concurrent.ThreadLocal

class DI {
    @ThreadLocal
    companion object {
        val shared = DI()
    }

    val networkService: INetworkService = NetworkService()
    val newsApi: NewsApi by lazy {   NewsApi() }

}