package com.azharkova.newsapp.service

import io.ktor.http.*

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

    constructor(errorType: ErrorType) {
        this.type = errorType
    }

    constructor(message: String) {
        this.message = message
    }
}