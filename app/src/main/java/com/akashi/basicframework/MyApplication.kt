package com.akashi.basicframework

import android.app.Application
import android.widget.Toast

class MyApplication : Application() {

    companion object {
        lateinit var instance: MyApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}

fun toast(str: String) {
    Toast.makeText(MyApplication.instance, str, Toast.LENGTH_SHORT).show()
}