package com.akashi.basicframework.startup.manager

import android.os.Handler
import android.os.Looper
import java.util.concurrent.*

/**
 * 线程池管理
 */
private val CPU_COUNT = Runtime.getRuntime().availableProcessors()
private val CORE_POOL_SIZE = 2.coerceAtLeast((CPU_COUNT - 1).coerceAtMost(5))
private val MAX_POOL_SIZE = CORE_POOL_SIZE
private const val KEEP_ALIVE_TIME = 5L

/**
 * cpu密集型线程池
 */
val cpuExecutor: ThreadPoolExecutor = ThreadPoolExecutor(
    CORE_POOL_SIZE, MAX_POOL_SIZE,
    KEEP_ALIVE_TIME, TimeUnit.SECONDS, LinkedBlockingDeque<Runnable>(),
    Executors.defaultThreadFactory()
).also {
    it.allowCoreThreadTimeOut(true)
}

/**
 * io密集型线程池
 */
val ioExecutor: ExecutorService =
    Executors.newCachedThreadPool(Executors.defaultThreadFactory())

/**
 * 主线程专用线程池
 */
val mainExecutor: Executor = object : Executor {
    /**
     * 闭包
     */
    val handler = Handler(Looper.getMainLooper())

    override fun execute(command: Runnable?) {
        command?.let { handler.post(it) }
    }

}

