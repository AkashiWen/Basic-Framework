package com.akashi.basicframework

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.akashi.basicframework.test.livedata.LiveDataActivity
import com.akashi.basicframework.business.user.UserActivity
import com.akashi.basicframework.test.crash.CrashActivity
import com.akashi.basicframework.test.file.ExternalFileActivity
import com.akashi.basicframework.test.hook.HookSuccessActivity
import com.akashi.common.util.clickJitter
import com.akashi.common.util.intentTo

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<AppCompatButton>(R.id.btn).clickJitter {
            intentTo(UserActivity::class.java)
        }

        findViewById<AppCompatButton>(R.id.btn_livedata).clickJitter {
            intentTo(LiveDataActivity::class.java)
        }

        findViewById<AppCompatButton>(R.id.btn_hook_ams).clickJitter {
            // 如果没有登录过，就会自动跳转登录页面
            intentTo(HookSuccessActivity::class.java, true)
        }

        findViewById<AppCompatButton>(R.id.btn_file).clickJitter {
            // 测试外部存储API
            intentTo(ExternalFileActivity::class.java)
        }

        findViewById<AppCompatButton>(R.id.btn_crash_handler).clickJitter {
            // 崩溃测试
            intentTo(CrashActivity::class.java)
        }
    }

}