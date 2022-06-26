package com.akashi.user

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.akashi.route.USER_PROFILE
import com.alibaba.android.arouter.facade.annotation.Route

@Route(path = USER_PROFILE)
class UserProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
    }

}