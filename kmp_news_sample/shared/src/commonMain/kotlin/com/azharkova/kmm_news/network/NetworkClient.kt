package com.azharkova.kmm_news.network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*


expect fun createHttpClient() : HttpClient

class NetworkClient {
    var httpClient = createHttpClient()

    suspend inline fun<reified T> getData(path: String): Result<T> {
        try {
            val result = httpClient.get(path).body<T>()
            return Result.success(result)
        }catch (e: Exception) {
            return Result.failure(e)
        }
    }

}