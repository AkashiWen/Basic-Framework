package com.akashi.basicframework

import android.app.Application
import android.content.Context
import com.akashi.basicframework.crash.restartApp
import com.akashi.testing.crash.AfterCrashRestartActivity
import com.akashi.testing.hook.LoginActivity
import com.akashi.common.hook.HookUtil
import com.akashi.common.logger.Logger
import com.akashi.common.logger.logD
import com.akashi.common.logger.logE
import com.akashi.common.logger.logW
import com.akashi.common.util.toast
import com.akashi.opensource.lifecycle.watcher.registerOurLifecycleCallback
import xcrash.ICrashCallback
import xcrash.XCrash
import java.io.File

class MyApplication : Application() {

    companion object {
        lateinit var instance: MyApplication
    }

    private val xCrashCallback: ICrashCallback by lazy {
        ICrashCallback { logPath, emergency ->
            toast("发生崩溃，路径: ${logPath}, emergency: $emergency")
            logW("发生崩溃，路径: ${logPath}, emergency: $emergency")
            val logFile = File(logPath)
            logD("query log file result: $logFile")

            // 上传File
            upload(logFile)

            // 重启app，进入崩溃提示页面，并杀死当前进程
            restartApp(this, 50L, AfterCrashRestartActivity::class.java)
        }
    }

    private fun upload(logFile: File) {
        // TODO
    }


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        // Crash Handler
        XCrash.init(this, XCrash.InitParameters().apply {
            setJavaCallback(xCrashCallback)
            setAnrCallback(xCrashCallback)
            setNativeCallback(xCrashCallback)
        })
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        // 日志工具
        Logger.choseLogger(Logger.TIMBER)?.init()

        // 监听Activity生命周期
        this.registerOurLifecycleCallback()

        // hook工具 —— hook AMS
        try {
            HookUtil.getInstance()
                .with(this)
                .hookTo(LoginActivity::class.java)
                .hookAMS()
        } catch (e: Throwable) {
            logE(e)
        }
    }
}