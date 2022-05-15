package com.akashi.common.file.r

import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import com.akashi.common.file.r.annotation.DBField

open class FileRequest(absPath: String = "${Environment.DIRECTORY_DOWNLOADS}/unnamed") :
    BaseFileRequest(absPath) {

    @RequiresApi(Build.VERSION_CODES.Q)
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