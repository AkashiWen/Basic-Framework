package com.akashi.common.util

import android.content.Intent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.akashi.common.constant.LoginConstant

fun View.clickJitter(block: () -> Unit) {
    this.setOnClickListener {
        isClickable = false
        block.invoke()
        it.postDelayed({
            isClickable = true
        }, 300)
    }
}

fun AppCompatActivity.intentTo(activity: Class<out AppCompatActivity>, needsLogin: Boolean = false) {
    Intent(this, activity).run {
        if (needsLogin) {
            putExtra(LoginConstant.STR_NEEDS_LOGIN, true)
        }
        startActivity(this)
    }
}