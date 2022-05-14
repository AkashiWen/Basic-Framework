package com.akashi.common.file.r.api

import android.content.Context
import com.akashi.common.file.r.BaseRequest
import com.akashi.common.file.r.FileResponse

interface IFile {
    fun <T : BaseRequest> query(context: Context, request: T): FileResponse
    fun <T : BaseRequest> create(context: Context, request: T): FileResponse
    fun <T : BaseRequest> copy(context: Context, request: T): FileResponse
    fun <T : BaseRequest> renameTo(context: Context, request: T, where: T): FileResponse
    fun <T : BaseRequest> delete(context: Context, request: T): FileResponse
}