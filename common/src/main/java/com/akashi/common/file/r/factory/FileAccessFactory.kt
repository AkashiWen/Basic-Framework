package com.akashi.common.file.r.factory

import android.os.Build
import android.os.Environment
import androidx.annotation.RequiresApi
import com.akashi.common.file.r.BaseFileRequest
import com.akashi.common.file.r.api.IFile
import com.akashi.common.file.r.impl.FileStoreImpl
import com.akashi.common.file.r.impl.FileStoreQImpl
import java.io.File


fun getIFile(baseFileRequest: BaseFileRequest): IFile {

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        if (!Environment.isExternalStorageLegacy()) {
            setFileType(baseFileRequest)
            FileStoreQImpl.instance()
        } else {
            setFile(baseFileRequest)
            FileStoreImpl()
        }
    } else {
        FileStoreImpl()
    }

}

fun setFile(baseFileRequest: BaseFileRequest) {
    baseFileRequest.file = File(baseFileRequest.absPath)
}

/**
 * 根据类型，放入不同的系统文件夹
 */
@RequiresApi(Build.VERSION_CODES.R)
private fun setFileType(baseFileRequest: BaseFileRequest) {
    val fullPath = baseFileRequest.absPath

    val type = if (fullPath.endsWith(BaseFileRequest.SUFFIX_MP3)
        || fullPath.endsWith(BaseFileRequest.SUFFIX_FLAC)
    ) {
        FileStoreQImpl.AUDIO
    } else if (fullPath.endsWith(BaseFileRequest.SUFFIX_PNG)
        || fullPath.endsWith(BaseFileRequest.SUFFIX_JPG)
    ) {
        FileStoreQImpl.IMAGE
    } else if (fullPath.endsWith(BaseFileRequest.SUFFIX_MP4)
        || fullPath.endsWith(BaseFileRequest.SUFFIX_AVI)
        || fullPath.endsWith(BaseFileRequest.SUFFIX_RMVB)
    ) {
        FileStoreQImpl.VIDEO
    } else {
        FileStoreQImpl.DOWNLOADS
    }

    baseFileRequest.type = type
}