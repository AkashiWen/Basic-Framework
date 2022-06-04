package com.akashi.opensource.lifecycle.watcher

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import com.akashi.opensource.util.noOpDelegate

/**
 * 监听Activity生命周期
 */
fun Application.registerOurLifecycleCallback(
    callbacks: Application.ActivityLifecycleCallbacks = mDefaultLifecycleCallbacks
) {
    this.registerActivityLifecycleCallbacks(callbacks)
}


private const val TAG = "LifecycleWatcher"

/**
 * 为ActivityLifecycleCallbacks实现动态代理
 * 默认实现的Application生命周期监听
 *
 * 根据需要创建自定义的
 */
private val mDefaultLifecycleCallbacks =
    object : Application.ActivityLifecycleCallbacks by noOpDelegate() {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            Log.i(TAG, "onActivityCreated: ${activity.localClassName} created")
        }

        override fun onActivityDestroyed(activity: Activity) {
            Log.i(TAG, "onActivityCreated: ${activity.localClassName} destroyed")
        }
    }
