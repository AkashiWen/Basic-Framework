package com.akashi.testing

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.akashi.route.TESTING
import com.alibaba.android.arouter.facade.annotation.Route

@Route(path = TESTING)
class TestingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_testing)
    }
}