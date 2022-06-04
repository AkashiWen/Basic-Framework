package com.akashi.test.hook

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.akashi.test.R

/**
 * 没有登录状态是无法进入这里的
 */
class HookSuccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hook_succes)
    }
}