package com.akashi.basicframework.test.livedata

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.akashi.basicframework.R
import com.akashi.basicframework.toast
import com.akashi.common.actions.clickJitter
import com.akashi.common.actions.intentTo
import com.akashi.common.date.now
import com.akashi.common.livedata.LiveDataBus

class LiveDataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_data)

        // G1 - 发送一个非粘性事件消息
        findViewById<AppCompatButton>(R.id.btn_post_non_sticky).clickJitter {
            val eventName = "Event1"
            LiveDataBus.get().with(eventName, String::class.java, false)
                .postValue("Akashi post non sticky value to $eventName at ${now()}")
            alert()
        }

        // G1 - 发送一个粘性事件消息
        findViewById<AppCompatButton>(R.id.btn_post_sticky).clickJitter {
            val eventName = "Event2"
            LiveDataBus.get().with(eventName, String::class.java)
                .postValue("Akashi post sticky value to $eventName at ${now()}")
            alert()
        }

        // G1 - 跳转到测试粘性/非粘性事件Activity
        findViewById<AppCompatButton>(R.id.btn_non_sticky).clickJitter {
            intentTo(TestNonStickyLiveDataActivity::class.java)
        }
    }

    private fun alert() {
        toast("已发送消息，可点击跳转按钮")
    }
}