package com.akashi.basicframework.test.crash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.akashi.basicframework.R
import com.akashi.common.util.clickJitter
import xcrash.XCrash

class CrashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crash)

        findViewById<AppCompatButton>(R.id.btn_test_crash_main).clickJitter {
            XCrash.testJavaCrash(false)
        }
        findViewById<AppCompatButton>(R.id.btn_test_crash_work).clickJitter {
            XCrash.testJavaCrash(true)
        }
    }
}