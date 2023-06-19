package com.akashi.opensource.network.api

import com.akashi.opensource.network.model.UserModel
import retrofit2.Response
import retrofit2.http.GET

interface MeApi {

    @GET("/me")
    fun fetchMe(): Response<UserModel>
}