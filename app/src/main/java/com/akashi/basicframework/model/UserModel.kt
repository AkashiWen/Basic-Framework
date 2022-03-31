package com.akashi.basicframework.model

import com.akashi.basicframework.bean.User

class UserModel : IBaseModel<User> {

    override fun fetch(listener: IBaseModel.ModelLoaderListener<User>) {
        listener.onComplete(User("Akashi", "137****4763"))
    }

}