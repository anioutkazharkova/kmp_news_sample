package com.azharkova.newsapp.threads

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

actual val defaultDispatcher: CoroutineContext = Dispatchers.Default

actual val uiDispatcher: CoroutineContext = Dispatchers.Main