package com.azharkova.newsapp.network

import com.azharkova.newsapp.service.ContentResponse
import com.azharkova.newsapp.service.ErrorResponse
import io.ktor.client.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.decodeFromString

expect fun createHttpClient():HttpClient

class NetworkClient {
    val httpClient = createHttpClient()


    suspend inline fun <reified T> getData(path: String): ContentResponse<T> {
        var contentResponse = ContentResponse<T>()

        try {

            val json = httpClient.get<String> {
                url {
                    protocol = URLProtocol.HTTPS
                    host = NetworkConfig.shared.apiUrl
                    encodedPath = path
                    header("X-Api-Key", NetworkConfig.shared.apiKey)
                }
            }
            print(json)
            val jsonDecoder = kotlinx.serialization.json.Json { ignoreUnknownKeys = true }
            val result = jsonDecoder.decodeFromString<T>(json)

            contentResponse.content = result
        } catch (ex: Exception) {
            val error = ErrorResponse(ex.message.toString())
            contentResponse.errorResponse = error
            print(ex.message.toString())
        }
        return contentResponse
    }


    suspend inline fun <reified T> getListData(path: String):ContentResponse<List<T>> {
        var contentResponse = ContentResponse<List<T>>()

        try {
            val json = httpClient.get<String> {
                url {
                    protocol = URLProtocol.HTTPS
                    host = NetworkConfig.shared.apiUrl
                    encodedPath = path
                    header("X-Api-Key", NetworkConfig.shared.apiKey)
                }
            }
            print(json)
            print(json)
            val jsonDecoder = kotlinx.serialization.json.Json { ignoreUnknownKeys = true }
            val result = jsonDecoder.decodeFromString<List<T>>(json)
            contentResponse.content = result
        } catch (ex: Exception) {
            val error = ErrorResponse(ex.message.toString())
            contentResponse.errorResponse = error
            print(ex.message.toString())
        }
        return contentResponse
    }
}