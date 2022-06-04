package com.akashi.common.util

import android.os.Build
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun now(): String? {
    val current = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        LocalDateTime.now()
    } else {
        return null
    }

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")

    return current.format(formatter)
}