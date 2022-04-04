package com.akashi.basicframework.startup.run

import android.content.Context
import android.os.Process
import com.akashi.basicframework.startup.Result
import com.akashi.basicframework.startup.Startup
import com.akashi.basicframework.startup.manager.StartupManager
import com.akashi.basicframework.startup.manager.StartupManagerCache

/**
 * Startup的Runnable封装
 * 使其支持被线程池调度
 */
class StartupRunnable(
    private val context: Context,
    private val startup: Startup<*>,
    private val startupManager: StartupManager
) : Runnable {

    override fun run() {
        // 1. 给线程设置优先级
        Process.setThreadPriority(startup.getThreadPriority())
        // 2. 立刻阻塞入度不为0的，入度为0则继续执行
        startup.toWait()
        // 3. 执行，保存结果
        val result = startup.create(context)
        StartupManagerCache.instance.saveInitializedComponent(startup.javaClass, Result(result))
        // 4. 当前任务执行完成，调用后续任务的toNotify()
        startupManager.notifyChildren(startup)
    }
}