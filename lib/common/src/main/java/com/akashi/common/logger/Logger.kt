package com.akashi.common.logger

import android.annotation.SuppressLint
import android.util.Log
import com.akashi.common.logger.timber.mTimberLog

class Logger {

    companion object {
        const val TIMBER = "timber"

        @SuppressLint("LogNotTimber")
        fun choseLogger(type: String): ILogger? {

            when (type) {
                TIMBER -> mTimberLog
                else -> {
                    Log.e("Logger", "choseLogger: cannot find type: $type for ILogger ")
                    null
                }
            }.let {
                logger = it
                return it
            }
        }
    }

}

private var logger: ILogger? = null

fun logI(info: String, tag: String = "TAG") = logger?.i(info, tag)
fun logD(msg: String, tag: String = "TAG") = logger?.d(msg, tag)
fun logW(warn: String, tag: String = "TAG") = logger?.w(warn, tag)
fun logE(e: Throwable, tag: String = "TAG") = logger?.e(e, tag)
fun logE(error: String, tag: String = "TAG") = logger?.e(error, tag)
fun logWtf(wtf: String, tag: String = "TAG") = logger?.wtf(wtf, tag)