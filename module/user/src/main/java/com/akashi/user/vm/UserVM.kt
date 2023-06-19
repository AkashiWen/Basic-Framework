package com.akashi.user.vm

import androidx.lifecycle.MutableLiveData
import com.akashi.common.base.mvvm.BaseViewModel
import com.akashi.user.model.UserBean
import com.akashi.user.repo.UserRepo

class UserVM(private val userRepo: UserRepo) : BaseViewModel() {

    val mUser = MutableLiveData<UserBean>()

    fun fetchUser() = apiLaunch {
        onRequest {
            userRepo.requestUser()
        }

        onResponse {
            mUser.postValue(it.data)
        }
    }

    fun fetchUserOverrideOnStart(onStart: () -> Boolean) = apiLaunch {
        onStart {
            onStart.invoke()
        }

        onRequest {
            userRepo.requestUser()
        }

        onResponse {
            mUser.postValue(it.data)
        }
    }


}