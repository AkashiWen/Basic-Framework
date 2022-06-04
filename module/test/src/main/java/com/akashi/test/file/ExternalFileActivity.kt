package com.akashi.test.file

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.akashi.common.file.checkFileStoragePermission
import com.akashi.common.file.getIFile
import com.akashi.common.file.r.FileRequest
import com.akashi.common.file.r.ImageRequest
import com.akashi.common.util.clickJitter
import com.akashi.common.util.toast
import com.akashi.test.R
import java.io.IOException

const val IMAGE_DISPLAY_NAME = "avatar_me.png"

class ExternalFileActivity : AppCompatActivity() {

    private lateinit var tvResponse: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_external_file)
        this.checkFileStoragePermission()

        findViewById<AppCompatButton>(R.id.btn_create).clickJitter { createPicture() }
        findViewById<AppCompatButton>(R.id.btn_del).clickJitter { delete() }
        findViewById<AppCompatButton>(R.id.btn_update).clickJitter {
            update("avatar_updated_${System.currentTimeMillis()}.png")
        }
        findViewById<AppCompatButton>(R.id.btn_query).clickJitter { query() }

        tvResponse = findViewById(R.id.tv_response)
    }

    private fun create() {
        val request = FileRequest().apply {
            this.path = "AkashiPath"
            this.displayName = "AkashiDisplay"
            this.title = "AkashiTitle"
        }
        val response = getIFile(request).create(this, request)
        toast("$response")
    }

    private fun delete() {
        ImageRequest("xxx.png").apply {
            this.displayName = IMAGE_DISPLAY_NAME
        }.let {
            getIFile(it).delete(this, it)
        }.let {
            tvResponse.text = "$it"
        }
    }

    private fun update(newName: String) {
        val where = ImageRequest("xxx.png").apply {
            this.displayName = IMAGE_DISPLAY_NAME
        }
        val set = ImageRequest("yyy.png").apply {
            this.displayName = newName
        }
        getIFile(where).renameTo(this, set, where).let {
            tvResponse.text = "$it"
        }
    }

    private fun query() {
        ImageRequest("xxx.png").apply {
            this.path = "chatroom"
            this.displayName = IMAGE_DISPLAY_NAME
        }.let {
            getIFile(it).query(this, it)
        }.let {
            tvResponse.text = "$it"
        }
    }


    /**
     * 创建图片
     */
    private fun createPicture() {
        ImageRequest("xxx.png").apply {
            this.path = "chatroom"
            this.displayName = "avatar_me.png"
        }.let {
            getIFile(it).create(this, request = it)
        }.let {
            val uri = it.uri ?: return@let
            val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.map)
            try {
                val os = contentResolver.openOutputStream(uri)
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, os)
                os?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            tvResponse.text = "$it"
        }
    }
}