package com.akashi.user.model

import com.akashi.opensource.network.BaseClientBean
import com.akashi.opensource.network.model.UserModel

data class UserBean(
    var name: String? = null,
    var phone: Int? = null
) : BaseClientBean<UserModel, UserBean> {
    override fun convert(apiModel: UserModel?): UserBean {
        return UserBean(
            name = apiModel?.name,
            phone = apiModel?.phone?.toInt()
        )
    }
}
