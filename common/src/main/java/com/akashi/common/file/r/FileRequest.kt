package com.akashi.common.file.r

import android.annotation.SuppressLint
import android.os.Environment
import android.provider.MediaStore
import com.akashi.common.file.api.BaseFileRequest
import com.akashi.common.file.r.annotation.DBField

open class FileRequest(absPath: String = "${Environment.DIRECTORY_DOWNLOADS}/unnamed") :
    BaseFileRequest(absPath) {

    @SuppressLint("InlinedApi")
    @DBField(MediaStore.Downloads.RELATIVE_PATH)
    var path: String = ""
        get() {
            if (field.isNotBlank()) {
                return "${Environment.DIRECTORY_DOWNLOADS}/${field}/"
            }
            return ""
        }

    @DBField(MediaStore.Downloads.DISPLAY_NAME)
    var displayName: String = ""

    @DBField(MediaStore.Downloads.TITLE)
    var title: String = ""
}