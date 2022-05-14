package com.akashi.common.file.r.impl

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import com.akashi.common.file.r.BaseFileRequest
import com.akashi.common.file.r.FileResponse
import com.akashi.common.file.r.annotation.DBField
import com.akashi.common.file.r.api.IFile

/**
 * api > 29
 */
@RequiresApi(Build.VERSION_CODES.Q)
class FileStoreQImpl private constructor() : IFile {

    companion object {
        private val instance by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            FileStoreQImpl()
        }

        fun instance() = instance

        const val AUDIO = "Audio"
        const val VIDEO = "Video"
        const val IMAGE = "Pictures"
        const val DOWNLOADS = "Downloads"
        //        const val DOCUMENT = "Documents"
    }

    /**
     * key: type from BaseFileRequest
     * value: like MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
     */
    private val map = mutableMapOf<String, Uri>()

    init {
        map[AUDIO] = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        map[VIDEO] = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        map[IMAGE] = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
//        map[DOCUMENT] = MediaStore.Document.EXTERNAL_CONTENT_URI
        map[DOWNLOADS] = MediaStore.Downloads.EXTERNAL_CONTENT_URI
    }

    override fun <T : BaseFileRequest> query(context: Context, request: T): FileResponse {
        TODO("Not yet implemented")
    }

    override fun <T : BaseFileRequest> create(context: Context, request: T): FileResponse {
        val uri = map[request.type] ?: throw Exception("uri not found")
        val contentResolver = context.contentResolver
        // request -> contentValues
        val contentValues = convertToContentValues(request)
        val resUri = contentResolver.insert(uri, contentValues)
        return FileResponse(true, resUri, null)
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
        TODO("Not yet implemented")
    }

    /**
     * 反射解析并转换
     */
    private fun convertToContentValues(request: BaseFileRequest): ContentValues {
        // result
        val contentValues = ContentValues()

        for (field in request.javaClass.declaredFields) {
            val dbField = field.getAnnotation(DBField::class.java) ?: continue
            // 注解标记值
            val name = dbField.value
            // 字段名
            val fieldName = field.name
            // 字段值
            var fieldValue = ""
            val firstLetter = fieldName.toCharArray()[0].uppercaseChar()
            val theRest = fieldName.substring(1)
            val methodName = "get${firstLetter}${theRest}"

            try {
                request.javaClass.getMethod(methodName).let {
                    fieldValue = it.invoke(request) as String
                }
            } catch (e: Throwable) {
                Log.e("TAG", "convertToContentValues: ${e.printStackTrace()}")
            }

            if (name.isNotBlank() && fieldValue.isNotBlank()) {
                contentValues.put(name, fieldValue)
            }
        }
        return contentValues
    }
}