package com.akashi.basicframework.startup.tasks

import android.content.Context
import android.util.Log
import com.akashi.basicframework.startup.AndroidStartup
import com.akashi.basicframework.startup.Startup

/**
 * 1. 隐私协议
 */
class PrivacyTask : AndroidStartup<Boolean>() {
    override fun create(context: Context): Boolean {
        Log.i("PrivacyTask", "create: privacy task..")
        return true
    }

    override fun dependencies(): List<Class<out Startup<*>>>? {
        return listOf(SafetyCheckTask::class.java)
    }

    override fun getDependenciesCount(): Int = 1
}