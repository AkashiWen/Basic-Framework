package com.akashi.payment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import com.akashi.common.util.clickJitter
import com.akashi.common.util.intentTo

class PaymentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        findViewById<AppCompatButton>(R.id.btn_to_user_route).clickJitter {
            // intent to UserProfileActivity
        }
    }
}