package com.akashi.opensource.network

import okhttp3.*
import java.io.IOException

class RequestInvoker {

    private val client by lazy {
        OkHttpClient()
    }

    fun doGet(url: String) {
        Request.Builder().url(url).build().let { request ->
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    TODO("Not yet implemented")
                }

                override fun onResponse(call: Call, response: Response) {
                    TODO("Not yet implemented")
                }

            })
        }
    }
}