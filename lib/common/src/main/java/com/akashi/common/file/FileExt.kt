package com.akashi.common.file

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build

fun Activity.checkFileStoragePermission(): Boolean {
    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M
        && this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED
    ) {
        this.requestPermissions(
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
            1
        )
    }
    return false
}
