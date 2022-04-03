package com.akashi.basicframework.startup.tasks

import android.content.Context
import android.util.Log
import com.akashi.basicframework.startup.AndroidStartup
import com.akashi.basicframework.startup.Startup

/**
 * 4.中间件
 */
class MiddlewareTask : AndroidStartup<Boolean>() {
    override fun create(context: Context): Boolean {
        Log.i("MiddlewareTask", "create: middleware task..")
        return true
    }

    override fun dependencies(): List<Class<out Startup<*>>> {
        return listOf(SDKTask::class.java, DataBaseTask::class.java)
    }

    override fun getDependenciesCount(): Int = 2
}