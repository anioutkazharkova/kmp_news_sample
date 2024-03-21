package com.azharkova.kmm_news.threads

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

expect val ioDispatcher: CoroutineDispatcher
expect val uiDispatcher: CoroutineDispatcher

val ioD = Dispatchers.IO
val uiD = Dispatchers.Main