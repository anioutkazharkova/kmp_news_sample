package com.azharkova.newsapp.threads

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Runnable
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue
import platform.darwin.dispatch_queue_main_t
import kotlin.coroutines.CoroutineContext
import kotlin.native.concurrent.freeze

actual val defaultDispatcher: CoroutineContext = MainDispatcher

actual val uiDispatcher: CoroutineContext = MainDispatcher


@ThreadLocal
object MainDispatcher : CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        dispatch_async(dispatch_get_main_queue()) {
            try {
                block.run().freeze()
            } catch (err: Throwable) {
                throw err
            }
        }
    }
}