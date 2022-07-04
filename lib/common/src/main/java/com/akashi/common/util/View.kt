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

fun toast(str: String?) {
    Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show()
}