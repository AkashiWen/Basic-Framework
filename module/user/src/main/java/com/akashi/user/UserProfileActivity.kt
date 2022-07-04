package com.akashi.user

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.akashi.common.base.mvvm.BaseActivity
import com.akashi.common.base.mvvm.BaseViewModel
import com.akashi.common.util.clickJitter
import com.akashi.common.util.toast
import com.akashi.route.USER_PROFILE
import com.akashi.user.vm.UserViewModel
import com.alibaba.android.arouter.facade.annotation.Route
import org.koin.androidx.viewmodel.ext.android.viewModel

@Route(path = USER_PROFILE)
class UserProfileActivity : BaseActivity() {

    private val mUserVM by viewModel<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        initView()
        initVM()
    }

    private fun initView() {

        mUserVM.registerClientCallbackListener(object : BaseViewModel.NoActionClientCallback() {
            override fun onApiStart() {
                toast("start loading user..")
            }

            override fun onApiFinal() {
                toast("loading finish..!!")
            }

            override fun onApiError(e: Throwable?, toast: String?) {
                super.onApiError(e, toast)
                toast(toast)
            }
        })

        findViewById<AppCompatButton>(R.id.btn_fetch_user).clickJitter {
            mUserVM.fetchUser()
        }
        findViewById<AppCompatButton>(R.id.btn_fetch_user_2).clickJitter {
            mUserVM.fetchUserOverrideOnStart {
                toast("start loading user -- 覆盖了！！")
                true
            }
        }

    }

    private fun initVM() {
        mUserVM.mUser.observe(this) {
            toast(it.toString())
        }

    }


}