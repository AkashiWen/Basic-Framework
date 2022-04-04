package com.akashi.basicframework.startup.tasks

import android.content.Context
import android.util.Log
import com.akashi.basicframework.startup.AndroidStartup
import com.akashi.basicframework.startup.manager.mainExecutor
import java.util.concurrent.Executor

/**
 * 0. 签名校验等
 */
class SafetyCheckTask : AndroidStartup<Boolean>() {
    override fun create(context: Context): Boolean {
        Log.i("SafetyCheckTask", "create: safety check task..")
        return true
    }

    override fun callCreateOnMainThread(): Boolean = true
    override fun executor(): Executor = mainExecutor
}