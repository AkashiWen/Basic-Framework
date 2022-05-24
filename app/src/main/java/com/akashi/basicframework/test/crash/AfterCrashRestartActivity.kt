package com.akashi.basicframework.test.crash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.akashi.basicframework.R

/**
 * 崩溃后启动的页面
 */
class AfterCrashRestartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_after_crash_restart)
    }
}