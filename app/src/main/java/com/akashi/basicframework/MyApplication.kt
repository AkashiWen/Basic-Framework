package com.akashi.basicframework

import android.app.Application
import android.content.Context
import android.widget.Toast
import com.akashi.basicframework.crash.restartApp
import com.akashi.basicframework.test.crash.AfterCrashRestartActivity
import com.akashi.basicframework.test.hook.LoginActivity
import com.akashi.common.actions.initLog
import com.akashi.common.actions.logD
import com.akashi.common.actions.logE
import com.akashi.common.actions.logW
import com.akashi.common.hook.HookUtil
import com.akashi.common.watchers.registerOurLifecycleCallback
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
        initLog()

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

fun toast(str: String) {
    Toast.makeText(MyApplication.instance, str, Toast.LENGTH_SHORT).show()
}