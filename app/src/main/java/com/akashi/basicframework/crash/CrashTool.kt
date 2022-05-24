package com.akashi.basicframework.crash

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.akashi.common.actions.logW
import kotlin.system.exitProcess

@SuppressLint("UnspecifiedImmutableFlag")
fun restartApp(context: Context, delay: Long, clazz: Class<out Any>) {
    Intent(context.applicationContext, clazz).apply {
        this.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }.let {
        if (it.component != null) {
            // 如果类名已经设置，我们强制它模拟启动器启动。
            // 如果我们不这样做，如果你从错误活动重启，然后按home，
            // 然后从启动器启动活动，主活动将在backstack上出现两次。
            // 这很可能不会有任何有害的影响，因为如果你设置了Intent组件，
            // if将始终启动，而不考虑此处指定的操作。
            it.action = Intent.ACTION_MAIN
            it.addCategory(Intent.CATEGORY_LAUNCHER)
        }

        PendingIntent.getActivity(
            context.applicationContext,
            0,
            it,
            PendingIntent.FLAG_ONE_SHOT
        ).let { restartIntent ->
            (context.getSystemService(Context.ALARM_SERVICE) as AlarmManager).apply {
                this.set(
                    AlarmManager.RTC,
                    System.currentTimeMillis() + delay,
                    restartIntent
                )
            }
            logW("restart app --- 重启app --- 闹钟模式 $clazz")
        }
    }

    killCurrentProcess(true)
}

fun killCurrentProcess(isThrow: Boolean) {
    android.os.Process.killProcess(android.os.Process.myPid())
    if (isThrow) {
        exitProcess(10)
    } else {
        exitProcess(0)
    }
}
