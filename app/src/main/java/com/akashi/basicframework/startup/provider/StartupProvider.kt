package com.akashi.basicframework.startup.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import com.akashi.basicframework.startup.manager.StartupManager

class StartupProvider : ContentProvider() {
    override fun onCreate(): Boolean {

        val context = context ?: return false

        val startups = discoverAndInitialize(context, javaClass.name)

        StartupManager.Builder()
            .addAllStartup(startups)
            .build(context)
            .start()
            .await()

        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? = null

    override fun getType(uri: Uri): String? = null

    override fun insert(uri: Uri, values: ContentValues?): Uri? = null

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int = 0

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int = 0
}