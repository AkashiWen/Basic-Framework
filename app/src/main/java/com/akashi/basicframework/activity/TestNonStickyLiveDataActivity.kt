package com.akashi.basicframework.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.akashi.basicframework.R
import com.akashi.basicframework.toast

/**
 * 测试非粘性事件，这个Activity会观察liveData，但期望第一次的值不被接收
 * 由于liveData是静态，第二次打开会被接收
 */
class TestNonStickyLiveDataActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_non_sticky_live_data)

        liveData.observe(this) {
            toast(it)
            Log.i("NonStickyActivity", "TestNonStickyLiveDataActivity: $it")
        }
    }
}