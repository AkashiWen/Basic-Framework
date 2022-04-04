package com.akashi.basicframework.startup

import android.os.Process
import com.akashi.basicframework.startup.manager.ioExecutor
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executor

/**
 * Startup 任务默认值
 */
abstract class AndroidStartup<T> : Startup<T> {

    private val mCountDownLatch = CountDownLatch(this.getDependenciesCount())

    // ------------ Startup --------------

    override fun dependencies(): List<Class<out Startup<*>>>? = null

    override fun getDependenciesCount(): Int = this.dependencies()?.size ?: 0

    // ------------ Dispatcher -------------

    /**
     * 默认不在主线程执行
     */
    override fun callCreateOnMainThread(): Boolean = false

    /**
     * 默认不需要主线程等待
     */
    override fun waitOnMainThread(): Boolean = false

    /**
     * 默认使用io密集型线程池
     */
    override fun executor(): Executor = ioExecutor

    /**
     * 默认最高优先级
     */
    override fun getThreadPriority(): Int = Process.THREAD_PRIORITY_DEFAULT

    override fun toWait() {
        try {
            // Causes the current thread to wait until the latch has counted down to zero
            mCountDownLatch.await()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    override fun toNotify() {
        // Decrements the count of the latch, releasing all waiting threads if the count reaches zero.
        mCountDownLatch.countDown()
    }

}