package com.akashi.basicframework.business.user.model

import com.akashi.common.base.mvp.IBaseModel
import com.akashi.basicframework.business.user.bean.User

class UserModel : IBaseModel<User> {

    override fun fetch(listener: IBaseModel.ModelLoaderListener<User>) {
        listener.onComplete(User("Akashi", "137****4763"))
    }

}