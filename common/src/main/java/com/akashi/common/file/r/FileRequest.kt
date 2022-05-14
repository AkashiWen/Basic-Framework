package com.akashi.common.file.r

import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import com.akashi.common.file.r.annotation.DBField
import java.io.File

class FileRequest(file: File) : BaseRequest(file) {

    @RequiresApi(Build.VERSION_CODES.Q)
    @DBField(MediaStore.Downloads.RELATIVE_PATH)
    var path: String = ""
        get() = "${Environment.DIRECTORY_DOWNLOADS}/${field}"

    @DBField(MediaStore.Downloads.DISPLAY_NAME)
    var displayName: String = ""

    @DBField(MediaStore.Downloads.TITLE)
    var title: String = ""
}