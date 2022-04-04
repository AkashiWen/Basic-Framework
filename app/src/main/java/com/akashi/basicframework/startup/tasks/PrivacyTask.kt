package com.akashi.basicframework.startup.tasks

import android.content.Context
import android.util.Log
import com.akashi.basicframework.startup.AndroidStartup
import com.akashi.basicframework.startup.Startup
import com.akashi.basicframework.startup.manager.mainExecutor
import java.util.concurrent.Executor

/**
 * 1. 隐私协议
 */
class PrivacyTask : AndroidStartup<Boolean>() {
    override fun create(context: Context): Boolean {
        Log.i("PrivacyTask", "create: privacy task..")
        return true
    }

    override fun dependencies(): List<Class<out Startup<*>>> {
        return listOf(SafetyCheckTask::class.java)
    }

    override fun callCreateOnMainThread(): Boolean = true
    override fun executor(): Executor = mainExecutor

}