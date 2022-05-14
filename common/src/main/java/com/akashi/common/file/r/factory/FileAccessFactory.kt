package com.akashi.common.file.r.factory

import android.os.Build
import android.os.Environment
import androidx.annotation.RequiresApi
import com.akashi.common.file.r.BaseRequest
import com.akashi.common.file.r.api.IFile
import com.akashi.common.file.r.impl.FileStoreImpl
import com.akashi.common.file.r.impl.MediaStoreAccessImpl


fun getIFile(baseRequest: BaseRequest): IFile {

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        if (!Environment.isExternalStorageLegacy()) {
            setFileType(baseRequest)
            MediaStoreAccessImpl.instance()
        } else {
            FileStoreImpl()
        }
    } else {
        FileStoreImpl()
    }

}

/**
 * 根据类型，放入不同的系统文件夹
 */
@RequiresApi(Build.VERSION_CODES.Q)
private fun setFileType(baseRequest: BaseRequest) {
    val absPath = baseRequest.file.absolutePath
    if (absPath.endsWith(".mp3") || absPath.endsWith(".flac")) {
        MediaStoreAccessImpl.AUDIO
    } else if (absPath.endsWith(".png") || absPath.endsWith(".jpg")) {
        MediaStoreAccessImpl.IMAGE
    } else if (absPath.endsWith(".mp4") || absPath.endsWith(".avi") || absPath.endsWith(".rmvb")) {
        MediaStoreAccessImpl.VIDEO
    } else {
        MediaStoreAccessImpl.DOWNLOADS
    }.let {
        baseRequest.type = it
    }
}