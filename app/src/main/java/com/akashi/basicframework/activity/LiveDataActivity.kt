package com.akashi.basicframework.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.Observer
import com.akashi.basicframework.MyApplication
import com.akashi.basicframework.R
import com.akashi.basicframework.toast
import com.akashi.common.actions.clickJitter
import com.akashi.common.actions.intentTo

class LiveDataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_data)

        findViewById<AppCompatButton>(R.id.btn_post_value).clickJitter {
            liveData.postValue("it is now: ${System.currentTimeMillis()}, Akashi!")
        }

        findViewById<AppCompatButton>(R.id.btn_non_sticky).clickJitter {
            intentTo(TestNonStickyLiveDataActivity::class.java)
        }
    }
}