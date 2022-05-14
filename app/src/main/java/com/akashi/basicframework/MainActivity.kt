package com.akashi.basicframework

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.akashi.basicframework.test.livedata.LiveDataActivity
import com.akashi.basicframework.business.user.UserActivity
import com.akashi.basicframework.test.file.ExternalFileActivity
import com.akashi.basicframework.test.hook.HookSuccessActivity
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
            intentTo(HookSuccessActivity::class.java, true)
        }

        findViewById<AppCompatButton>(R.id.btn_file).clickJitter {
            intentTo(ExternalFileActivity::class.java)
        }
    }

}