package com.akashi.basicframework.topologySort.tasks

import android.content.Context
import android.os.SystemClock
import android.util.Log
import com.akashi.basicframework.startup.AndroidStartup
import com.akashi.basicframework.startup.Startup

class Task5 : AndroidStartup<Any>() {

    override fun create(context: Context): Any {
        Log.i("start up", "create: task5")
        SystemClock.sleep(1_000)
        return "task5"
    }

    override fun dependencies(): List<Class<out Startup<*>>>? {
        return listOf(Task3::class.java)
    }

    override fun getDependenciesCount(): Int = 1
}