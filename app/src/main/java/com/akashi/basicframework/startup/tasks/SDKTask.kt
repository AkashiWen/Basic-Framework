package com.akashi.basicframework.startup.tasks

import android.content.Context
import android.util.Log
import com.akashi.basicframework.startup.AndroidStartup
import com.akashi.basicframework.startup.Startup

/**
 * 2. 各种sdk初始化
 */
class SDKTask : AndroidStartup<Boolean>() {

    companion object {
        private val TAG = SDKTask::class.java.simpleName
    }

    override fun create(context: Context): Boolean {
        Log.i(TAG, "create: SDK Task...")
        return true
    }

    override fun dependencies(): List<Class<out Startup<*>>>? {
        return listOf(PrivacyTask::class.java)
    }

    override fun getDependenciesCount(): Int = 1

}