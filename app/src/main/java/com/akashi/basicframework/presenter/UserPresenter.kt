package com.akashi.basicframework.presenter

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import com.akashi.basicframework.bean.User
import com.akashi.basicframework.model.IBaseModel
import com.akashi.basicframework.model.UserModel
import com.akashi.basicframework.view.UserView

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

    /**
     * lifecycle touch
     */
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        Log.i("UserPresenter", ":onCreate()")
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        Log.i("UserPresenter", ":onDestroy()")
    }

}