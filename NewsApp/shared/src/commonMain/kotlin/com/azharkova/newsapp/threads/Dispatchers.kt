package com.azharkova.newsapp.threads

import kotlinx.coroutines.CoroutineDispatcher
import kotlin.coroutines.CoroutineContext

expect val defaultDispatcher: CoroutineContext

expect val uiDispatcher: CoroutineContext
