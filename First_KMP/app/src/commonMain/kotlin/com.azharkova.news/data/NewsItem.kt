package com.azharkova.news.data

import kotlinx.serialization.Serializable

@Serializable
data class NewsItem(
    var author: String?,
    var title: String?,
    var description: String?,
   var url: String?,
    var urlToImage: String?,
    var content: String?,
    var publishedAt: String
)