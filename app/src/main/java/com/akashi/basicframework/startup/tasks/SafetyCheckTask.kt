package com.akashi.basicframework.startup.tasks

import android.content.Context
import android.util.Log
import com.akashi.basicframework.startup.AndroidStartup

/**
 * 0. 签名校验等
 */
class SafetyCheckTask : AndroidStartup<Boolean>() {
    override fun create(context: Context): Boolean {
        Log.i("SafetyCheckTask", "create: safety check task..")
        return true
    }
}