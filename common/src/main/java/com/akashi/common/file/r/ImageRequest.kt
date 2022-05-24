package com.akashi.common.file.r

import android.annotation.SuppressLint
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import com.akashi.common.file.api.BaseFileRequest
import com.akashi.common.file.r.annotation.DBField

class ImageRequest(absPath: String) : BaseFileRequest(absPath) {

    @DBField(MediaStore.Images.Media.MIME_TYPE)
    var mimeType: String = "image/png"

    @SuppressLint("InlinedApi")
    @DBField(MediaStore.Downloads.RELATIVE_PATH)
    var path: String = ""
        get() {
            if (field.isNotEmpty()) {
                return "${Environment.DIRECTORY_PICTURES}/${field}/"
            }
            return ""
        }

    @DBField(MediaStore.Downloads.DISPLAY_NAME)
    var displayName: String = ""

}