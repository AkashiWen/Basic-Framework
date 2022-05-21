package com.akashi.common.watchers

import android.app.Activity
import android.app.Application
import android.os.Bundle

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
            TODO("Not yet implemented")
        }

        override fun onActivityDestroyed(activity: Activity) {
            TODO("Not yet implemented")
        }
    }
