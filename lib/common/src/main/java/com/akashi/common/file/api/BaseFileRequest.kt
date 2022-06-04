package com.akashi.common.file.api

import java.io.File

open class BaseFileRequest(val absPath: String) {

    companion object {
        const val SUFFIX_MP3 = ".mp3"
        const val SUFFIX_FLAC = ".flac"
        const val SUFFIX_PNG = ".png"
        const val SUFFIX_JPG = ".jpg"
        const val SUFFIX_MP4 = ".mp4"
        const val SUFFIX_AVI = ".avi"
        const val SUFFIX_RMVB = ".rmvb"
    }

    /**
     * api版本大于等于29，使用ContentResolver沙箱机制操作文件
     *
     */
    var type: String = ""

    /**
     * api版本低于29，提供File对象直接操作
     */
    var file: File? = null
}