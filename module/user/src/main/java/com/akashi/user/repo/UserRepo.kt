package com.akashi.user.repo

import com.akashi.common.base.api.AResponse
import com.akashi.opensource.network.BaseRepo
import com.akashi.opensource.network.api.MeApi
import com.akashi.opensource.network.model.UserModel
import com.akashi.user.model.UserBean
import kotlinx.coroutines.delay

class UserRepo(private val meApi: MeApi) : BaseRepo() {

    suspend fun requestUser(): AResponse<UserBean> {
        delay(3000)

        meApi.fetchMe().apply {
            return convertToAResponse(this) {
                UserBean().convert(this.body())
            }
        }
    }
}