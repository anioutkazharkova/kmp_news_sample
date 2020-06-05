package com.azharkova.news.http

import com.azharkova.news.dispatcher.*
import com.azharkova.news.service.response.ContentResponse
import com.azharkova.news.service.response.ErrorResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.URLProtocol
import kotlinx.coroutines.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.list

interface INetworkService {
    suspend fun<T> getData(path: String, serializer: KSerializer<T>,completed: (ContentResponse<T>)->Unit)

    suspend fun<T> getListData(path: String, serializer: KSerializer<T>, completed:ContentResponse<List<T>>)
}

class NetworkService : INetworkService{
    private val httpClient = HttpClient()

    override suspend fun<T> getData(path: String, serializer: KSerializer<T>,completed: (ContentResponse<T>)->Unit){
        //Для ktor используем свой скоуп
        ktorScope {

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
               val response = kotlinx.serialization.json.Json.nonstrict.parse(serializer, json)

               contentResponse.content = response
           } catch (ex: Exception) {
               val error = ErrorResponse()
               error.message = ex.message.toString()
               contentResponse.errorResponse = error
               print(ex.message.toString())
           }
            //Ответ отдаем в UI-поток
           withContext(uiDispatcher) {
               completed(contentResponse)
           }
       }
    }

    override suspend fun<T> getListData(path: String, serializer: KSerializer<T>, completed: ContentResponse<List<T>>) {
        ktorScope {
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
                val response = kotlinx.serialization.json.Json.nonstrict.parse(serializer.list, json)
                contentResponse.content = response
            } catch (ex: Exception) {
                val error = ErrorResponse()
                error.message = ex.message.toString()
                contentResponse.errorResponse = error
                print(ex.message.toString())
            }
            withContext(uiDispatcher) {
            //    completed(contentResponse)
            }
        }
    }

}