package com.akashi.basicframework.test.file

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.akashi.basicframework.R
import com.akashi.basicframework.toast
import com.akashi.common.actions.clickJitter
import com.akashi.common.file.checkFileStoragePermission
import com.akashi.common.file.r.FileRequest
import com.akashi.common.file.r.ImageRequest
import com.akashi.common.file.r.BaseFileRequest
import com.akashi.common.file.r.factory.getIFile
import java.io.IOException

class ExternalFileActivity : AppCompatActivity() {

    lateinit var tvResponse: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_external_file)
        this.checkFileStoragePermission()

        findViewById<AppCompatButton>(R.id.btn_create).clickJitter { createPicture() }
        findViewById<AppCompatButton>(R.id.btn_del).clickJitter { }
        findViewById<AppCompatButton>(R.id.btn_update).clickJitter { }
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

    private fun delete() {}

    private fun update() {}

    private fun query() {
        ImageRequest("xxx.png").apply {
            this.path = "chatroom"
            this.displayName = "avatar_me.png"
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