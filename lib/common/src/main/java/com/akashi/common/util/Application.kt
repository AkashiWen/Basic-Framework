package com.akashi.common.util

import android.annotation.SuppressLint
import android.app.Application
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Parcelable
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.akashi.common.constant.LoginConstant
import com.akashi.common.logger.logD
import com.akashi.common.logger.logE
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.ArrayList
import kotlin.coroutines.resume

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
 * 带参数的跳转
 */
inline fun <reified T : AppCompatActivity> AppCompatActivity.intentTo(builder: Intent.() -> Unit = {}) {
    Intent(this, T::class.java).run {
        builder.invoke(this)
        startActivity(this)
    }
}


/**
 * 请求指定权限
 *
 * @param permission 权限名称，例如 Manifest.permission.CAMERA
 * @return true：权限已经被授予；false：权限未被授予
 */
suspend fun AppCompatActivity.requestPermission(permission: String): Boolean {
    if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
        return true
    }

    return suspendCancellableCoroutine { continuation ->

        val permissionRequest =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    continuation.resume(true)
                } else {
                    continuation.resume(false)
                }
            }

        permissionRequest.launch(permission)
    }
}

inline fun <reified T : Parcelable> Intent?.getJsonArrayExtra(key: String): ArrayList<T>? {

    this?.getStringExtra(key)?.let {
        return try {
            val type = object : TypeToken<ArrayList<T>>() {}.type
            Gson().fromJson(it, type)
        } catch (e: Exception) {
            logE(e)
            null
        }
    } ?: return null
}

inline fun <reified T : Parcelable> Intent?.getJsonExtra(key: String): T? {

    this?.getStringExtra(key)?.let {
        return try {
            Gson().fromJson(it, T::class.java)
        } catch (e: Exception) {
            logE(e)
            null
        }
    } ?: return null
}