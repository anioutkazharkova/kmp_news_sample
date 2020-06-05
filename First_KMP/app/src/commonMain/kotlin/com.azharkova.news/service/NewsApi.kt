package com.azharkova.news.service

import com.azharkova.news.data.NewsItem
import com.azharkova.news.data.NewsList
import com.azharkova.news.http.NetworkService
import com.azharkova.news.service.response.ContentResponse
import com.azharkova.news.service.response.ErrorResponse
import io.ktor.client.HttpClient
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.URLProtocol
import io.ktor.http.cio.Response
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.list
import kotlinx.serialization.json.Json
import kotlinx.serialization.parse
import kotlinx.serialization.serializer
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
class NewsApi {
    @ThreadLocal
    companion object {
        val shared = NewsApi()
    }

   val networkService = NetworkService()

    suspend fun getNewsList(completed: (ContentResponse<NewsList>)->Unit){
        var contentResponse = ContentResponse<List<NewsItem>>()
        val path = "v2/top-headlines?language=en"
         networkService.getData(path, NewsList.serializer(),completed)
    }
}