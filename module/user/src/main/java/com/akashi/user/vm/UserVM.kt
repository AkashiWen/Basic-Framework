package com.akashi.user.vm

import androidx.lifecycle.MutableLiveData
import com.akashi.common.base.mvvm.BaseViewModel
import kotlinx.coroutines.delay

class UserVM : BaseViewModel() {

    val mUser = MutableLiveData<User>()

    fun fetchUser() = apiLaunch {
        onRequest {
            requestUser()
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
            requestUser()
        }

        onResponse {
            mUser.postValue(it.data)
        }
    }

    private suspend fun requestUser(): Response<User> {
        delay(3000)
        return Response(code = Response.OK, data = User(name = "Akashi", phone = 137))
    }

    data class User(val name: String, val phone: Int)
}