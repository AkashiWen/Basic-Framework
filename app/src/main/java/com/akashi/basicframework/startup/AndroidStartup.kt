package com.akashi.basicframework.startup

/**
 * Startup 任务默认值
 */
abstract class AndroidStartup<T> : Startup<T> {

    override fun dependencies(): List<Class<out Startup<*>>>? = null

    override fun getDependenciesCount(): Int = this.dependencies()?.size ?: 0
}