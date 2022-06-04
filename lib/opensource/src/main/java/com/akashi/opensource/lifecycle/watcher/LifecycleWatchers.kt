package com.akashi.opensource.lifecycle.watcher

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.akashi.common.logger.logD
import com.akashi.common.util.noOpDelegate

/**
 * 监听Activity生命周期
 */
fun Application.registerOurLifecycleCallback(
    callbacks: Application.ActivityLifecycleCallbacks = mDefaultLifecycleCallbacks
) {
    this.registerActivityLifecycleCallbacks(callbacks)
}


/**
 * 为ActivityLifecycleCallbacks实现动态代理
 * 默认实现的Application生命周期监听
 *
 * 根据需要创建自定义的
 */
private val mDefaultLifecycleCallbacks =
    object : Application.ActivityLifecycleCallbacks by noOpDelegate() {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            logD("${activity.localClassName} created", "LifecycleWatcher")
        }

        override fun onActivityDestroyed(activity: Activity) {
            logD("${activity.localClassName} destroyed", "LifecycleWatcher")
        }
    }
