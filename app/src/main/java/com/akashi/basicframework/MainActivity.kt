package com.akashi.basicframework

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.akashi.basicframework.livedata.LiveDataActivity
import com.akashi.basicframework.business.user.UserActivity
import com.akashi.basicframework.hook.HookAMSActivity
import com.akashi.common.actions.clickJitter
import com.akashi.common.actions.intentTo

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
            intentTo(HookAMSActivity::class.java)
        }
    }

}