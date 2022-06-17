package com.akashi.payment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.akashi.common.util.clickJitter
import com.akashi.route.RouterManager
import com.akashi.route.USER_PROFILE

class PaymentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        findViewById<AppCompatButton>(R.id.btn_to_user_route).clickJitter {
            // intent to UserProfileActivity
            // 按照Kotlin配置方式，依然报
            // ARouter::There is no route match the path [/user/activity], in group [user][ ]
            RouterManager.instance.navigationTo(USER_PROFILE)
        }
    }
}