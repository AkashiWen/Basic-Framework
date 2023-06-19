package com.akashi.opensource.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val okHttpClient = OkHttpClient.Builder()
    .connectTimeout(3, TimeUnit.SECONDS)
    .readTimeout(3, TimeUnit.SECONDS)
    .writeTimeout(3, TimeUnit.SECONDS)
    .retryOnConnectionFailure(true)
    .build()

val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl("https://api.github.com/")
    .client(okHttpClient)
    .addConverterFactory(GsonConverterFactory.create())
    .build()