package com.akashi.common.util

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Proxy


/**
 * 通用动态代理
 * source from LeakCanary
 */
inline fun <reified T : Any> noOpDelegate(): T {
    val javaClass = T::class.java
    return Proxy.newProxyInstance(
        javaClass.classLoader, arrayOf(javaClass), NO_OP_HANDLER
    ) as T
}

val NO_OP_HANDLER = InvocationHandler { _, _, _ ->
    // no op
}