package com.akashi.common.file.r

import android.net.Uri
import java.io.File

data class FileResponse(
    private val isSuccess: Boolean,
    private val uri: Uri?,
    private val file: File?
)