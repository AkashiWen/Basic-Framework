package com.akashi.basicframework.startup

import java.util.concurrent.Executor

/**
 * 线程池分发器
 */
interface Dispatcher {

    /**
     * 是否在主线程执行
     */
    fun callCreateOnMainThread(): Boolean

    /**
     * 是否需要主线程等待该任务执行完毕
     * callCreateOnMainThread()返回false才有意义
     */
    fun waitOnMainThread(): Boolean

    /**
     * 让每个任务都可以指定自己执行在哪个线程池
     */
    fun executor(): Executor

    /**
     * 指定线程优先级
     */
    fun getThreadPriority(): Int

    /**
     * 等待，阻塞住
     */
    fun toWait()

    /**
     * 有父任务执行完毕
     * 通知CountDownLatch计数器-1
     */
    fun toNotify()

}