package com.azharkova.news.dispatcher

import kotlinx.coroutines.*
import platform.darwin.*
import kotlin.coroutines.CoroutineContext
import kotlin.native.concurrent.SharedImmutable
import kotlin.native.concurrent.freeze

actual val uiDispatcher: CoroutineContext
    get() = MainDispatcher

actual val defaultDispatcher: CoroutineContext
    get() = MainDispatcher

//actual  val ioDispatcher: CoroutineContext
//    get() = IosDefaultDispatcher

actual fun ktorScope(block: suspend () -> Unit) {
    GlobalScope.launch(MainDispatcher) { block() }
}

actual val ApplicationDispatcher: CoroutineDispatcher =
    NsQueueDispatcher(dispatch_get_main_queue())

internal class NsQueueDispatcher(
    private val dispatchQueue: dispatch_queue_t
) : CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        dispatch_async(dispatchQueue) {
            block.run()
        }
    }
}

/**
 * iOS doesn't have a default UI thread dispatcher like [Dispatchers.Main], so we have to implement it ourself.
 */
@ThreadLocal
private object  MainDispatcher: CoroutineDispatcher() {

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

@ThreadLocal
private object IosDefaultDispatcher : CoroutineDispatcher() {

    @ExperimentalUnsignedTypes
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT.toLong(), 0.toULong())) {
            try {
                block.run().freeze()
            } catch (err: Throwable) {
                throw err
            }
        }
    }
}
