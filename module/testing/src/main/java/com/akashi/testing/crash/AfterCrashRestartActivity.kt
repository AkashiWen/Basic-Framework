package com.akashi.testing.crash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.akashi.testing.R

/**
 * 崩溃后启动的页面
 */
class AfterCrashRestartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_after_crash_restart)
    }
}