package com.akashi.basicframework.startup.sort

import com.akashi.basicframework.startup.Startup
import com.akashi.basicframework.startup.StartupSortStore

/**
 * 实现拓扑排序
 * 单元测试见 @see TopologySortTest
 */
class TopologySort {

    companion object {

        fun sort(startupList: List<Startup<*>>): StartupSortStore? {
            // 原始表
            val startupMap: MutableMap<Class<out Startup<*>>, Startup<*>> = mutableMapOf()
            // 入度表 Startup -> 度
            val inDegreeMap: MutableMap<Class<out Startup<*>>, Int> = mutableMapOf()
            // 0度表
            val zeroDegree: java.util.ArrayDeque<Class<out Startup<*>>> = java.util.ArrayDeque()
            // 依赖关系表
            val startupChildrenMap: MutableMap<Class<out Startup<*>>, MutableList<Class<out Startup<*>>>> =
                mutableMapOf()

            // 1. 填表
            for (startup in startupList) {
                startupMap[startup.javaClass] = startup

                val dependenciesCount = startup.getDependenciesCount()
                // 1.1 记录每个任务入度数
                inDegreeMap[startup.javaClass] = dependenciesCount

                // 1.2 单独记录入度0任务
                if (dependenciesCount == 0) {
                    zeroDegree.add(startup.javaClass)
                } else {
                    // 1.3 遍历依赖的任务 组成依赖关系表
                    for (parent in startup.dependencies() ?: return null) {
                        var childrenList = startupChildrenMap[parent]
                        if (childrenList == null) {
                            childrenList = mutableListOf()
                            startupChildrenMap[parent] = childrenList
                        }
                        childrenList.add(startup.javaClass)
                    }
                }
            }

            // 2. 开始排序，删除图中入度0的定点，然后更新全图，直到完成所有
            val result = mutableListOf<Startup<*>>()
//            val main = mutableListOf<Startup<*>>()
//            val thread = mutableListOf<Startup<*>>()

            while (!zeroDegree.isEmpty()) {
                val cls = zeroDegree.poll()!!
                startupMap[cls]?.let {
                    result.add(it)
                    // 2.1 优化：防止主线程因等待运行完毕而阻塞
//                    (if (it.callCreateOnMainThread()) {
//                        main
//                    } else {
//                        thread
//                    }).add(it)
                }

                // 删除此入度0的任务
                if (startupChildrenMap[cls] != null) {
                    val children = startupChildrenMap[cls]
                    for (child in children!!) {
                        val num = inDegreeMap[child]!!
                        // 更新入度表度数
                        inDegreeMap[child] = num - 1

                        // 如果遇到0度就加入队列
                        if (num - 1 == 0) {
                            zeroDegree.offer(child)
                        }
                    }
                }
            }

            result.run {
                // 所有子线程放前面，避免阻塞
//                this.addAll(thread)
//                this.addAll(main)
            }

            return StartupSortStore(
                result.toList(),
                startupMap,
                startupChildrenMap.toMap()
            )
        }
    }
}
