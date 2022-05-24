package com.akashi.basicframework

import android.app.Application
import android.widget.Toast
import com.akashi.basicframework.test.hook.LoginActivity
import com.akashi.common.actions.initLog
import com.akashi.common.actions.logD
import com.akashi.common.actions.logE
import com.akashi.common.actions.logW
import com.akashi.common.hook.HookUtil
import com.akashi.common.watchers.registerOurLifecycleCallback
import xcrash.XCrash
import java.io.File

class MyApplication : Application() {

    companion object {
        lateinit var instance: MyApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        // 日志工具
        initLog()

        // Crash Handler
        XCrash.init(this, XCrash.InitParameters().apply {
            setJavaCallback { logPath, emergency ->
                toast("发生Java崩溃，路径: ${logPath}, emergency: $emergency")
                logW("发生Java崩溃，路径: ${logPath}, emergency: $emergency")
                val it = File(logPath)
                logD("query log file result: $it")

            }

            setAnrCallback { logPath, emergency ->
                toast("发生Anr，路径: ${logPath}, emergency: $emergency")
            }

            setNativeCallback { logPath, emergency ->
                toast("发生Native崩溃，路径: ${logPath}, emergency: $emergency")
            }
        })

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