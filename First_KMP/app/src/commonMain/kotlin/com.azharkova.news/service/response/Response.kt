package com.azharkova.news.service.response

import kotlin.Error

class ContentResponse<T> {
    var content: T? = null
    var errorResponse: ErrorResponse? = null
}

enum class ErrorType {
    Network, Auth, Tech, Other, NotFound , BadAnswer
}

class ErrorResponse {
    var code: Int = 0
    var errorType: String = ""
    var message: String = ""

    var type: ErrorType = ErrorType.Other
}