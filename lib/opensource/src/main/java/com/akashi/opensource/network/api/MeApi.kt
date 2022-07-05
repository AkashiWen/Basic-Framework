package com.akashi.opensource.network.api

import retrofit2.http.GET

interface MeApi {

    @GET("/me")
    fun fetchMe()
}