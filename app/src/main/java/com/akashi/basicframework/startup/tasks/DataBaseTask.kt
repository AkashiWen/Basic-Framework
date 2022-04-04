package com.akashi.basicframework.startup.tasks

import android.content.Context
import android.util.Log
import com.akashi.basicframework.startup.AndroidStartup
import com.akashi.basicframework.startup.Startup

/**
 * 3. 数据库
 */
class DataBaseTask : AndroidStartup<Boolean>() {
    override fun create(context: Context): Boolean {
        Log.i("DataBaseTask", "create: db test...")
        return true
    }

    override fun dependencies(): List<Class<out Startup<*>>> {
        return listOf(PrivacyTask::class.java)
    }
}