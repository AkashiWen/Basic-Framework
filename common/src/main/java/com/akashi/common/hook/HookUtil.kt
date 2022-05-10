package com.akashi.common.hook

import android.annotation.SuppressLint
import android.util.Log
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import kotlin.jvm.Throws

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


private class AmsInvocationHandler(val manager: Any?) : InvocationHandler {
    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any? {
        Log.i("dynamic proxy", "触发AMS动态代理 —— invoke: ${method?.name}")
//        return method?.invoke(manager, args)
        // (1)在invoke方法中接收可变长参数，在Kotlin语法中，数组是array，可变长参数类型是vararg，类型不匹配。
        // (2)Kotlin中数组转为可变长参数，通过前面加*符号。
        return method?.invoke(manager, *(args ?: emptyArray()))
    }

}