package com.azharkova.kmm_news.data

import kotlinx.serialization.Serializable

@Serializable
data class NewsItem(
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?
)