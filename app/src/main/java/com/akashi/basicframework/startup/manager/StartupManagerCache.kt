package com.akashi.basicframework.startup.manager

import com.akashi.basicframework.startup.Result
import com.akashi.basicframework.startup.Startup
import java.util.concurrent.ConcurrentHashMap

/**
 * Startup执行结果缓存
 */
class StartupManagerCache private constructor() {

    private val mInitializedComponents =
        ConcurrentHashMap<Class<out Startup<*>>, Result>()

    companion object {
        val instance by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { StartupManagerCache() }
    }

    fun saveInitializedComponent(cls: Class<out Startup<*>>, result: Result) {
        mInitializedComponents[cls] = result
    }

    fun hadInitialized(cls: Class<out Startup<*>>): Boolean = mInitializedComponents.contains(cls)

    fun obtainInitializedResult(cls: Class<out Startup<*>>): Result? = mInitializedComponents[cls]

    fun remove(cls: Class<out Startup<*>>) = mInitializedComponents.remove(cls)

    fun clear() = mInitializedComponents.clear()
}