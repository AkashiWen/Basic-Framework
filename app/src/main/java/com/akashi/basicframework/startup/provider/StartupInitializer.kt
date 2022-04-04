package com.akashi.basicframework.startup.provider

import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import com.akashi.basicframework.startup.Startup

private const val META_VALUE = "android.startup"

/**
 * 通过末端节点反向查找父节点依次初始化
 */
@Throws(Exception::class)
fun discoverAndInitialize(
    context: Context,
    providerName: String
): List<Startup<*>> {
    // 用来保存任务结构图（有向无环图）
    val startups = mutableMapOf<Class<out Startup<*>>, Startup<*>>()

    /// 获得manifest contentProvider#meta-data (PMS)
    val provider = ComponentName(context, providerName)
    val providerInfo = context
        .packageManager
        .getProviderInfo(provider, PackageManager.GET_META_DATA)

    val bundle = providerInfo.metaData
    bundle.keySet().find {
        bundle.getString(it) == META_VALUE
    }?.let {
        val clazz =
            Class.forName(it) // com.akashi.basicframework.startup.tasks.MiddlewareTask
        if (Startup::class.java.isAssignableFrom(clazz)) { // clazz是Startup的子类返回true
            doInitialize(clazz.newInstance() as Startup<*>, startups)
        }
    }

    return mutableListOf<Startup<*>>().apply {
        addAll(startups.values)
    }
}

/**
 * 递归查询父任务
 */
@Throws(Exception::class)
private fun doInitialize(
    startup: Startup<*>,
    startups: MutableMap<Class<out Startup<*>>, Startup<*>>
) {
    startups[startup.javaClass] = startup
    if (startup.getDependenciesCount() != 0) {
        // 遍历父任务
        for (dependency in startup.dependencies() ?: return) {
            doInitialize(dependency.newInstance(), startups)
        }
    }
}