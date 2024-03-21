package com.azharkova.kmm_news.threads

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Runnable
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue
import kotlin.coroutines.CoroutineContext
import kotlin.native.concurrent.ThreadLocal
import kotlin.native.concurrent.freeze

/*
actual val ioDispatcher: CoroutineDispatcher = Dispatchers.Default
actual val uiDispatcher: CoroutineDispatcher = Dispatchers.Main
*/
actual val ioDispatcher: CoroutineDispatcher = MainDispatcher
actual val uiDispatcher: CoroutineDispatcher = MainDispatcher

@ThreadLocal
object MainDispatcher : CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        dispatch_async(dispatch_get_main_queue()) {
            try {
                block.run().freeze()
            } catch (e: Exception) {}
        }
    }
}