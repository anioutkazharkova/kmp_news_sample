package com.azharkova.kmm_news.network.config

class NetworkConfig {
    companion object shared{
        val apiUrl = "newsapi.org"
        val apiKey = "5b86b7593caa4f009fea285cc74129e2"

        val header: HashMap<String, String> =  hashMapOf("X-Api-Key" to apiKey)
    }
}