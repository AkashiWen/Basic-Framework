package com.akashi.basicframework.hook

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.akashi.basicframework.R
import com.akashi.common.actions.clickJitter
import com.akashi.common.constant.isLogin

/**
 * 没有注册到AndroidManifest.xml
 * 使用hook打开
 */
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hook_ams)

        findViewById<AppCompatButton>(R.id.btn_login).clickJitter {
            isLogin = true
        }
        findViewById<AppCompatButton>(R.id.btn_logout).clickJitter {
            isLogin = false
        }
    }
}