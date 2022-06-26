package com.akashi.common.util

import android.annotation.SuppressLint
import android.app.Application
import android.content.Intent
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.akashi.common.constant.LoginConstant
import com.akashi.common.logger.logD
import com.akashi.common.logger.logE

private var mApplicationContext: Application? = null

@SuppressLint("PrivateApi")
fun getContext(): Application? {
    if (mApplicationContext != null) return mApplicationContext
    try {
        val aClass = Class.forName("android.app.ActivityThread")
        aClass.getMethod("currentApplication").also {
            it.isAccessible = true
        }.let {
            mApplicationContext = it.invoke(null) as? Application
        }
    } catch (e: Throwable) {
        logE(e)
    }
    return mApplicationContext
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

/**
 * 申请单个权限
 */
fun AppCompatActivity.requestPermission(permission: String) {
    when (PackageManager.PERMISSION_DENIED) {
        ContextCompat.checkSelfPermission(this, permission) -> {
            logD("check permission: $permission ,already granted")
        }
        else -> {
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    logD("grant permission: $permission success")
                } else {
                    logD("grant permission: $permission failed")
                }
            }.launch(permission)
        }
    }
}

/**
 * 申请多个权限
 */
fun AppCompatActivity.requestPermissions(vararg permissions: String) {
    // 找到未授权索引
    val deniedIndex = permissions.indexOfFirst {
        ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_DENIED
    }
    if (deniedIndex == -1) {
        permissions.forEach {
            logD("permission: $it already granted")
        }
    } else {
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { map ->
            map.entries.forEach {
                logD("permission: ${it.key}, granted: ${it.value}")
            }
        }.launch(permissions.copyOfRange(deniedIndex, permissions.size))
    }
}