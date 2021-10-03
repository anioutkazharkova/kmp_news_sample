package com.azharkova.newsapp.network

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.logging.*
import java.util.concurrent.TimeUnit

internal fun AndroidHttpClient() = HttpClient(OkHttp) {
    engine {
        config {
            retryOnConnectionFailure(true)
            connectTimeout(5, TimeUnit.SECONDS)
        }
    }
}


actual  fun createHttpClient():HttpClient {
    return AndroidHttpClient()
}