package com.akashi.common.file.r.impl

import android.content.Context
import com.akashi.common.file.api.BaseFileRequest
import com.akashi.common.file.api.FileResponse
import com.akashi.common.file.api.IFile

/**
 * api < 29
 */
class FileStoreImpl : IFile {
    override fun <T : BaseFileRequest> query(context: Context, request: T): FileResponse {
        return FileResponse(request.file != null, null, request.file)
    }

    override fun <T : BaseFileRequest> create(context: Context, request: T): FileResponse {
        if (request.file == null) return FileResponse(false)
        return FileResponse(true, null, request.file)
    }

    override fun <T : BaseFileRequest> copy(context: Context, request: T): FileResponse {
        TODO("Not yet implemented")
    }

    override fun <T : BaseFileRequest> renameTo(
        context: Context,
        request: T,
        where: T
    ): FileResponse {
        TODO("Not yet implemented")
    }

    override fun <T : BaseFileRequest> delete(context: Context, request: T): FileResponse {
        request.file?.delete()
        return FileResponse(true, null, null)
    }
}