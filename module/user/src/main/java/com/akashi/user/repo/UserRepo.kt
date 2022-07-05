package com.akashi.user.repo

import com.akashi.common.base.api.Response
import com.akashi.opensource.network.api.MeApi
import com.akashi.user.model.User
import kotlinx.coroutines.delay

class UserRepo(private val meApi: MeApi) {

    suspend fun requestUser(): Response<User> {
        delay(3000)

//        meApi.fetchMe()

        return Response(
            code = Response.OK,
            data = User(name = "Akashi", phone = 137)
        )
    }
}