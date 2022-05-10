package com.akashi.common.hook

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import com.akashi.common.constant.LoginConstant
import com.akashi.common.constant.isLogin
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import kotlin.jvm.Throws

class HookUtil private constructor() {

    // 防止反射破坏单例
    init {
        if (!flag) {
            flag = true
        } else {
            throw Throwable("SingleTon is being attacked.")
        }
    }

    companion object {
        var flag = false

        @JvmStatic
        fun getInstance() = Holder.instance
    }

    private class Holder {
        companion object {
            @SuppressLint("StaticFieldLeak")
            val instance = HookUtil()
        }
    }


    private lateinit var mContext: Context
    private lateinit var mTargetActivity: Class<out Activity>

    fun with(context: Context): HookUtil {
        mContext = context.applicationContext
        return this
    }

    fun hookTo(targetActivity: Class<out Activity>): HookUtil {
        mTargetActivity = targetActivity
        return this
    }


    /**
     * hook AMS 来调用startActivity
     */
    @SuppressLint("PrivateApi", "DiscouragedPrivateApi")
    @Throws(Throwable::class)
    fun hookAMS() {
        // 1. 反射ActivityManager
//    val amClz = Class.forName("android.app.ActivityManager") api<29
        val amClz = Class.forName("android.app.ActivityTaskManager")
        val singletonField = amClz.getDeclaredField("IActivityTaskManagerSingleton").also {
            it.isAccessible = true
        }
        val obj = singletonField.get(null)

        // 2. 反射Singleton
        val singletonClz = Class.forName("android.util.Singleton")
        val mInstance = singletonClz.getDeclaredField("mInstance").also {
            it.isAccessible = true
        }
        // val iActivityManager = singleton.instance
//    val manager = mInstance.get(iActivityManagerSingletonField)
        // api>29 通过上面mInstance.get()无法拿到manager
        val manager = singletonClz
            .getDeclaredMethod("get")
            .invoke(obj)

        // 动态代理
        val iActivityTaskManagerClz = Class.forName("android.app.IActivityTaskManager")
        val proxy = Proxy.newProxyInstance(
            Thread.currentThread().contextClassLoader,
            arrayOf(iActivityTaskManagerClz),
            AmsInvocationHandler(manager)
        )
        // singleton.instance = proxy 替换系统的mInstance
        mInstance.set(obj, proxy)
    }


    /**
     * dynamic proxy for IActivityTaskManager
     */
    private inner class AmsInvocationHandler(val manager: Any?) : InvocationHandler {
        override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any? {
            Log.i("dynamic proxy", "触发AMS动态代理 —— invoke: ${method?.name}")

            if ("startActivity" == method?.name && args != null) {
                args.find { it is Intent }?.let {
                    it as Intent
                }?.let {
                    if (!check(it)) {
                        return@let
                    }
                    // 原始意图
                    val originTargetActivity = it.component?.className
                    // 修改跳转到目标Activity
                    val componentName = ComponentName(mContext, mTargetActivity)
                    it.run {
                        component = componentName
                        // TODO 在LoginActivity还原目标意图
                        putExtra("originTarget", originTargetActivity)
                    }

                }
            }

//        return method?.invoke(manager, args)
            // (1)在invoke方法中接收可变长参数，在Kotlin语法中，数组是array，可变长参数类型是vararg，类型不匹配。
            // (2)Kotlin中数组转为可变长参数，通过前面加*符号。
            return method?.invoke(manager, *(args ?: emptyArray()))
        }

        private fun check(intent: Intent): Boolean {
            val needsLogin = intent.extras?.getBoolean(LoginConstant.STR_NEEDS_LOGIN) == true
            return needsLogin && !isLogin
        }

    }

}