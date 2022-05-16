package com.akashi.common.file.api

import android.content.Context

interface IFile {
    fun <T : BaseFileRequest> query(context: Context, request: T): FileResponse
    fun <T : BaseFileRequest> create(context: Context, request: T): FileResponse
    fun <T : BaseFileRequest> copy(context: Context, request: T): FileResponse
    fun <T : BaseFileRequest> renameTo(context: Context, request: T, where: T): FileResponse
    fun <T : BaseFileRequest> delete(context: Context, request: T): FileResponse
}