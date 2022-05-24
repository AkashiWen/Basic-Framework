package com.akashi.basicframework.test.crash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.akashi.basicframework.R

class CrashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crash)

        Handler(mainLooper).postDelayed({
            throw Throwable("测试触发崩溃")
        }, 2000)
    }
}