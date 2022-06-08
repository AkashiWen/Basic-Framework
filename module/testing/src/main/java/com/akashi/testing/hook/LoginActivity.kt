package com.akashi.testing.hook

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.akashi.common.constant.LoginConstant.STR_ORIGIN_INTENT
import com.akashi.common.constant.isLogin
import com.akashi.common.util.clickJitter
import com.akashi.common.util.toast
import com.akashi.testing.R

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
            toast("登录成功，自动跳转..")
            Handler(Looper.myLooper()!!).postDelayed({
                val originIntent = intent.extras?.getString(STR_ORIGIN_INTENT) ?: return@postDelayed
                val intent = Intent().apply {
                    component = ComponentName(this@LoginActivity, Class.forName(originIntent))
                }
                startActivity(intent)
                finish()
            }, 2000)
        }
        findViewById<AppCompatButton>(R.id.btn_logout).clickJitter {
            isLogin = false
            finish()
        }
    }
}