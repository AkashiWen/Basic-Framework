package com.akashi.common.actions

import android.content.Intent
import android.view.View
import androidx.appcompat.app.AppCompatActivity

fun View.clickJitter(block: () -> Unit) {
    this.setOnClickListener {
        isClickable = false
        block.invoke()
        it.postDelayed({
            isClickable = true
        }, 300)
    }
}

fun AppCompatActivity.intentTo(activity: Class<out AppCompatActivity>) {
    Intent(this, activity).run {
        startActivity(this)
    }
}