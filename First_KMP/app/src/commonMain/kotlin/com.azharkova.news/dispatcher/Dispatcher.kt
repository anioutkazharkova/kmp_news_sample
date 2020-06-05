package com.azharkova.news.dispatcher

import kotlinx.coroutines.CoroutineDispatcher
import kotlin.coroutines.CoroutineContext

expect val defaultDispatcher: CoroutineContext

expect val uiDispatcher: CoroutineContext

//expect val ioDispatcher: CoroutineContext

expect val ApplicationDispatcher: CoroutineDispatcher

//Свой скоуп для сетевого клиента
expect fun ktorScope(block: suspend () -> Unit)