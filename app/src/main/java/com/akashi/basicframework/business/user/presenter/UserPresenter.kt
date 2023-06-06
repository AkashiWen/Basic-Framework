package com.akashi.basicframework.business.user.presenter

import androidx.lifecycle.LifecycleOwner
import com.akashi.basicframework.business.user.bean.User
import com.akashi.basicframework.business.user.model.UserModel
import com.akashi.basicframework.business.user.view.UserView
import com.akashi.common.base.mvp.BasePresenter
import com.akashi.common.base.mvp.IBaseModel

class UserPresenter : BasePresenter<UserView>() {

    private val userModel = UserModel()

    fun fetchProfile() {

        userModel.fetch(object : IBaseModel.ModelLoaderListener<User> {
            override fun onComplete(bean: User) {
                view?.get()?.displayProfile(bean)
            }

            override fun onError(msg: String) {
                view?.get()?.showError(msg)
            }

        })
    }


    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
    }

}