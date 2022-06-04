package com.akashi.common.file.api

import android.net.Uri
import java.io.File

data class FileResponse(
    val isSuccess: Boolean,
    val uri: Uri? = null,
    val file: File? = null
)