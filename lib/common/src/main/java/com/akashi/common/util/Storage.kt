package com.akashi.common.util

import android.app.Application
import com.tencent.mmkv.MMKV

object MyStorage {

    fun init(context: Application) {
        MMKV.initialize(context)
    }

    private const val KEY_TOKEN = "token"

    private val _mmkv = MMKV.defaultMMKV()

    fun saveToken(token: String) {
        _mmkv.encode(KEY_TOKEN, token)
    }

    fun getToken(): String? {
        return _mmkv.decodeString(KEY_TOKEN)
    }
}
