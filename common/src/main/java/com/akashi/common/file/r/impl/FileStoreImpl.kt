package com.akashi.common.file.r.impl

import android.content.Context
import com.akashi.common.file.r.BaseFileRequest
import com.akashi.common.file.r.FileResponse
import com.akashi.common.file.r.api.IFile

/**
 * api < 29
 */
class FileStoreImpl : IFile {
    override fun <T : BaseFileRequest> query(context: Context, request: T): FileResponse {
        TODO("Not yet implemented")
    }

    override fun <T : BaseFileRequest> create(context: Context, request: T): FileResponse {
        TODO("Not yet implemented")
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
        return FileResponse(true, null, null)
    }
}