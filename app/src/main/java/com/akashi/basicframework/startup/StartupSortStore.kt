package com.akashi.basicframework.startup

data class StartupSortStore(
    /**
     * 排序结果
     */
    val result: List<Startup<*>>,
    /**
     * 原始数据
     */
    val startupMap: Map<Class<out Startup<*>>, Startup<*>>,
    /**
     * 依赖关系表
     */
    val startupChildrenMap: Map<Class<out Startup<*>>, MutableList<Class<out Startup<*>>>>
)