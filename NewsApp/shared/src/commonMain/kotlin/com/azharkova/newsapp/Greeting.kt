package com.azharkova.newsapp

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}