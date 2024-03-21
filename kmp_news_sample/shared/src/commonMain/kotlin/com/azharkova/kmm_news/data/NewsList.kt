package com.azharkova.kmm_news.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsList(@SerialName("articles") val articles: List<NewsItem>)
