package com.akashi.basicframework.topologySort

import com.akashi.basicframework.startup.sort.TopologySort
import com.akashi.basicframework.startup.tasks.*
import com.akashi.basicframework.topologySort.tasks.*
import org.junit.Test
import java.lang.StringBuilder

class TopologySortTest {

    @Test
    fun topology_sort() {
        TopologySort.sort(
//            listOf(Task2(), Task5(), Task4(), Task1(), Task3())
            listOf(DataBaseTask(), MiddlewareTask(), PrivacyTask(), SafetyCheckTask(), SDKTask())
        )?.let {
            val result = it.result
            val startupChildrenMap = it.startupChildrenMap

            println("===== 拓扑排序结果 ====")
            for (r in result) {
                print("${r.javaClass.simpleName} =>")
            }
            println("")
            println("===== 拓扑依赖关系图 ====")
            for (entry in startupChildrenMap.entries) {
                val parent = entry.key.simpleName
                val children = entry.value
                val stringBuilder = StringBuilder()
                for (child in children) {
                    stringBuilder.append(child.simpleName)
                    stringBuilder.append(", ")
                }
                println("$parent <= $stringBuilder")
            }
        }
    }

}