package com.akashi.basicframework.startup.manager

import android.content.Context
import android.os.Looper
import com.akashi.basicframework.startup.Startup
import com.akashi.basicframework.startup.StartupSortStore
import com.akashi.basicframework.startup.run.StartupRunnable
import com.akashi.basicframework.startup.sort.TopologySort
import java.lang.RuntimeException
import java.util.concurrent.CountDownLatch

class StartupManager(
    private val context: Context,
    private val tasks: List<Startup<*>>
) {

    private lateinit var startupSortStore: StartupSortStore

    // TODO 初始化位置和值
    private var countDownLatch: CountDownLatch? = null

    fun start(): StartupManager {
        // 1. 检查主线程
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw RuntimeException("请在主线程调用start()")
        }
        // 2. 排序
        startupSortStore =
            TopologySort.sort(tasks) ?: throw RuntimeException("启动框架发生异常，请检查Topology排序")

        for (startup in startupSortStore.result) {
            StartupRunnable(
                context = context, startup = startup, startupManager = this
            ).run {
                // 3 如果允许在主线程执行就立刻run()，否则就调用任务指定线程执行当前StartupRunnable
                if (startup.callCreateOnMainThread()) {
                    // Warn: 直接运行run()会阻塞主线程，卡住循环
                    // Solution: 优化拓扑排序算法，按照运行线程分别存放对应的集合
                    // TODO but: 导致result顺序出错，需要再思考
                    this.run()
                } else {
                    startup.executor().execute(this)
                }
            }
        }
        return this
    }

    // TODO
    fun await() {
        try {
            countDownLatch?.await()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    /**
     * 当前任务完成后，通知children的CountDownLatch减1
     */
    fun notifyChildren(startup: Startup<*>) {
        if (!startup.callCreateOnMainThread() && startup.waitOnMainThread()) {
            countDownLatch?.countDown()
        }
        // 获得startup的所有子任务
        if (startupSortStore.startupChildrenMap.containsKey(startup.javaClass)) {
            val children = startupSortStore.startupChildrenMap[startup.javaClass] ?: return
            for (cls in children) {
                // 通知子任务 startup父任务已经完成
                startupSortStore.startupMap[cls]?.toNotify()
            }
        }
    }

    class Builder {

        private val startupList = mutableListOf<Startup<*>>()

        fun addStartup(startup: Startup<*>): Builder {
            this.startupList.add(startup)
            return this
        }

        fun addAllStartup(startups: List<Startup<*>>): Builder {
            this.startupList.addAll(startups)
            return this
        }

        fun build(context: Context) = StartupManager(context, startupList)
    }
}