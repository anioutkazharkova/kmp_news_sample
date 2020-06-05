package com.azharkova.news.dispatcher

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

actual val uiDispatcher: CoroutineContext
    get() = Dispatchers.Main

actual val defaultDispatcher: CoroutineContext
    get() = Dispatchers.Default

//actual val ioDispatcher: CoroutineContext
  //  get() = Dispatchers.IO

actual val ApplicationDispatcher: CoroutineDispatcher = Dispatchers.Default

actual fun ktorScope(block: suspend () -> Unit) {
    	    GlobalScope.launch(Dispatchers.Main) { block() }
    	}