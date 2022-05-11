package com.akashi.basicframework.hook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.akashi.basicframework.R

/**
 * 没有登录状态是无法进入这里的
 */
class HookSuccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hook_succes)
    }
}