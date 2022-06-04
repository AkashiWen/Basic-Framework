package com.akashi.common.logger.timber

import com.akashi.common.logger.ILogger
import com.akashi.common.util.noOpDelegate
import timber.log.Timber

/**
 * Timber日志工具动态代理实现
 */
val mTimberLog = object : ILogger by noOpDelegate() {
    init {
        Timber.plant(object : Timber.DebugTree() {
            override fun createStackElementTag(element: StackTraceElement): String {
                return String.format("(%s:%s)", element.fileName, element.lineNumber)
            }
        })
    }

    override fun i(info: String, tag: String) = Timber.tag(tag).i("%s", info)
    override fun d(msg: String, tag: String) = Timber.tag(tag).d("%s", msg)
    override fun w(warn: String, tag: String) = Timber.tag(tag).w("%s", warn)
    override fun e(e: Throwable, tag: String) = Timber.tag(tag).e("%s", e.printStackTrace())
    override fun e(error: String, tag: String) = Timber.tag(tag).w("%s", error)
    override fun wtf(wtf: String, tag: String) = Timber.tag(tag).wtf("%s", wtf)
}