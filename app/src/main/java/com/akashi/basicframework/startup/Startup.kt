package com.akashi.basicframework.startup

import android.content.Context

/**
 * 抽象启动任务
 */
interface Startup<T>: Dispatcher {

    /**
     * 任务初始化代码
     */
    fun create(context: Context): T

    /**
     * 本任务依赖哪些任务
     */
    fun dependencies(): List<Class<out Startup<*>>>?

    /**
     * 入度数
     */
    fun getDependenciesCount(): Int
}