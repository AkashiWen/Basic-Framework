package com.akashi.basicframework

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.akashi.testing.livedata.LiveDataActivity
import com.akashi.basicframework.business.user.UserActivity
import com.akashi.testing.crash.CrashActivity
import com.akashi.testing.hook.HookSuccessActivity
import com.akashi.common.util.clickJitter
import com.akashi.common.util.intentTo
import com.akashi.testing.file.ExternalFileActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // MVP架构
        findViewById<AppCompatButton>(R.id.btn).clickJitter {
            intentTo<UserActivity>()
        }

        // 组件化
        findViewById<AppCompatButton>(R.id.btn_route).clickJitter {
            intentTo<MainRouteActivity>()
        }

        // livedata
        findViewById<AppCompatButton>(R.id.btn_livedata).clickJitter {
            intentTo<LiveDataActivity>()
        }

        findViewById<AppCompatButton>(R.id.btn_hook_ams).clickJitter {
            // 如果没有登录过，就会自动跳转登录页面
            intentTo<HookSuccessActivity>(true)
        }

        findViewById<AppCompatButton>(R.id.btn_file).clickJitter {
            // 测试外部存储API
            intentTo<ExternalFileActivity>()
        }

        findViewById<AppCompatButton>(R.id.btn_crash_handler).clickJitter {
            // 崩溃测试
            intentTo<CrashActivity>()
        }
    }

}