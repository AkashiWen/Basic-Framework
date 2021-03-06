package com.akashi.payment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.akashi.common.util.clickJitter
import com.akashi.route.PAYMENT
import com.akashi.route.RouterManager
import com.akashi.route.USER_PROFILE
import com.alibaba.android.arouter.facade.annotation.Route

@Route(path = PAYMENT)
class PaymentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        findViewById<AppCompatButton>(R.id.btn_to_user_route).clickJitter {
            // intent to UserProfileActivity
            RouterManager.instance.navigationTo(USER_PROFILE)
        }
    }
}