package com.azharkova.kmm_news

import com.azharkova.kmm_news.data.NewsList
import com.azharkova.kmm_news.network.NetworkClient
import com.azharkova.kmm_news.network.config.NetworkConfig

class NewsService constructor(private val networkClient: NetworkClient) {


    suspend fun loadNews():Result<NewsList> {
       return networkClient.getData<NewsList>(LOAD_CRUNCH)
    }

    companion object {
        private val LOAD_CRUNCH = "https://newsapi.org/v2/top-headlines?sources=techcrunch&apiKey=${NetworkConfig.apiKey}"
    }
}