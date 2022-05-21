package com.akashi.common.actions

import timber.log.Timber

fun initLog() {
    Timber.plant(object : Timber.DebugTree() {
        override fun createStackElementTag(element: StackTraceElement): String {
            return String.format("(%s:%s)", element.fileName, element.lineNumber)
        }
    })
}

fun logI(info: String, tag: String = "TAG") {
    Timber.tag(tag).i("%s", info)
}

fun logD(msg: String, tag: String = "TAG") {
    Timber.tag(tag).d("%s", msg)
}

fun logW(warn: String, tag: String = "TAG") {
    Timber.tag(tag).w("%s", warn)
}

fun logE(e: Throwable, tag: String = "TAG") {
    Timber.tag(tag).e("%s", e.printStackTrace())
}

fun logE(error: String, tag: String = "TAG") {
    Timber.tag(tag).e("%s", error)
}

fun logWtf(wtf: String, tag: String = "TAG") {
    Timber.tag(tag).wtf("%s", wtf)
}