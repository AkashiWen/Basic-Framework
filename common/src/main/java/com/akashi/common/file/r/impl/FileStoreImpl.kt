package com.akashi.common.file.r.impl

import android.content.Context
import com.akashi.common.file.r.BaseRequest
import com.akashi.common.file.r.FileResponse
import com.akashi.common.file.r.api.IFile

/**
 * api < 29
 */
class FileStoreImpl: IFile {
    override fun <T : BaseRequest> query(context: Context, request: T): FileResponse {
        TODO("Not yet implemented")
    }

    override fun <T : BaseRequest> create(context: Context, request: T): FileResponse {
        TODO("Not yet implemented")
    }

    override fun <T : BaseRequest> copy(context: Context, request: T): FileResponse {
        TODO("Not yet implemented")
    }

    override fun <T : BaseRequest> renameTo(context: Context, request: T, where: T): FileResponse {
        TODO("Not yet implemented")
    }

    override fun <T : BaseRequest> delete(context: Context, request: T): FileResponse {
        request.file.delete()
        return FileResponse(true, null, null)
    }
}