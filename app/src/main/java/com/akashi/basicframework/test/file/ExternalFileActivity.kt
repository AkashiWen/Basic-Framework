package com.akashi.basicframework.test.file

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import com.akashi.basicframework.R
import com.akashi.basicframework.toast
import com.akashi.common.actions.clickJitter
import com.akashi.common.file.checkFileStoragePermission
import com.akashi.common.file.r.FileRequest
import com.akashi.common.file.r.factory.getIFile
import java.io.File

class ExternalFileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_external_file)
        this.checkFileStoragePermission()

        findViewById<AppCompatButton>(R.id.btn_create).clickJitter { create() }
        findViewById<AppCompatButton>(R.id.btn_del).clickJitter { }
        findViewById<AppCompatButton>(R.id.btn_update).clickJitter { }
        findViewById<AppCompatButton>(R.id.btn_query).clickJitter { }
    }

    private fun create() {
        val request = FileRequest(File("Akashi")).apply {
            this.path = "AkashiPath"
            this.displayName = "AkashiDisplay"
            this.title = "AkashiTitle"
        }
        val response = getIFile(request).create(this, request)
        toast("$response")
    }

    private fun delete() {}

    private fun update() {}

    private fun query() {}
}