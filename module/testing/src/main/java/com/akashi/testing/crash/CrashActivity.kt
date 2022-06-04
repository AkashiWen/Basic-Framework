package com.akashi.testing.crash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.akashi.common.util.clickJitter
import com.akashi.testing.R

class CrashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crash)

        findViewById<AppCompatButton>(R.id.btn_test_crash_main).clickJitter {
            testJavaCrash(false)
        }
        findViewById<AppCompatButton>(R.id.btn_test_crash_work).clickJitter {
            testJavaCrash(true)
        }
    }

    @Throws(RuntimeException::class)
    private fun testJavaCrash(runInNewThread: Boolean) {
        if (runInNewThread) {
            val thread: Thread = object : Thread() {
                override fun run() {
                    throw RuntimeException("test java exception")
                }
            }
            thread.name = "xcrash_test_java_thread"
            thread.start()
        } else {
            throw RuntimeException("test java exception")
        }
    }
}