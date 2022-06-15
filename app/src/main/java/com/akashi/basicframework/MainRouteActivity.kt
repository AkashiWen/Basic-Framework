package com.akashi.basicframework

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import com.akashi.common.util.clickJitter
import com.akashi.common.util.intentTo
import com.akashi.payment.PaymentActivity
import com.akashi.testing.TestingActivity
import com.akashi.user.UserProfileActivity

/**
 * 组件化路由测试入口
 */
class MainRouteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_route)

        findViewById<AppCompatButton>(R.id.btn_to_payment).clickJitter {
            intentTo<PaymentActivity>()
        }
        findViewById<AppCompatButton>(R.id.btn_to_user).clickJitter {
            intentTo<UserProfileActivity>()
        }
        findViewById<AppCompatButton>(R.id.btn_to_testing).clickJitter {
            intentTo<TestingActivity>()
        }
    }
}