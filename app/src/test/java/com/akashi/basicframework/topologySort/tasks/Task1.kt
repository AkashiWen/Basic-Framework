package com.akashi.basicframework.topologySort.tasks

import android.content.Context
import android.os.SystemClock
import android.util.Log
import com.akashi.basicframework.startup.AndroidStartup

class Task1: AndroidStartup<Any>() {

    override fun create(context: Context): Any {
        Log.i("start up", "create: task1")
        SystemClock.sleep(1_000)
        return "task1"
    }
}