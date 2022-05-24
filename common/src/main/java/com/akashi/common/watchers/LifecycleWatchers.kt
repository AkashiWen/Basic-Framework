package com.akashi.common.watchers

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.akashi.common.actions.logD

/**
 * 监听Activity生命周期
 */
fun Application.registerOurLifecycleCallback() {
    this.registerActivityLifecycleCallbacks(lifecycleCallbacks)
}


/**
 * sample for noOpDelegate
 * 为ActivityLifecycleCallbacks实现动态代理
 */
private val lifecycleCallbacks =
    object : Application.ActivityLifecycleCallbacks by noOpDelegate() {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            logD("${activity.localClassName} created", "LifecycleWatcher")
        }

        override fun onActivityDestroyed(activity: Activity) {
            logD("${activity.localClassName} destroyed", "LifecycleWatcher")
        }
    }
