package com.akashi.common.logger

interface ILogger {
    fun init()
    fun i(info: String, tag: String = "TAG")
    fun d(msg: String, tag: String = "TAG")
    fun w(warn: String, tag: String = "TAG")
    fun e(error: String, tag: String = "TAG")
    fun e(e: Throwable, tag: String = "TAG")
    fun wtf(wtf: String, tag: String = "TAG")
}