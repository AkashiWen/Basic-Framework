package com.akashi.common.util

import android.content.Intent
import android.view.View
import android.widget.Toast
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

fun AppCompatActivity.intentTo(
    activity: Class<out AppCompatActivity>,
    needsLogin: Boolean = false
) {
    Intent(this, activity).run {
        if (needsLogin) {
            putExtra(LoginConstant.STR_NEEDS_LOGIN, true)
        }
        startActivity(this)
    }
}

/**
 * 使用inline和reified关键字优化泛型使用
 */
inline fun <reified T : AppCompatActivity> AppCompatActivity.intentTo(
    needsLogin: Boolean = false
) {
    Intent(this, T::class.java).run {
        if (needsLogin) {
            putExtra(LoginConstant.STR_NEEDS_LOGIN, true)
        }
        startActivity(this)
    }
}

fun toast(str: String) {
    Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show()
}