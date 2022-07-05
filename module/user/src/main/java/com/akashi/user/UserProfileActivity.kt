package com.akashi.user

import androidx.appcompat.widget.AppCompatButton
import com.akashi.common.base.mvvm.BaseActivity
import com.akashi.common.base.mvvm.BaseViewModel
import com.akashi.common.util.clickJitter
import com.akashi.common.util.toast
import com.akashi.route.USER_PROFILE
import com.akashi.user.vm.UserVM
import com.alibaba.android.arouter.facade.annotation.Route
import org.koin.androidx.viewmodel.ext.android.viewModel

@Route(path = USER_PROFILE)
class UserProfileActivity : BaseActivity(R.layout.activity_user_profile) {

    private val mUserVM by viewModel<UserVM>()
    
    override fun getViewModels(): List<BaseViewModel> = listOf(mUserVM)

    override fun initView() {

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

    override fun initViewModel() {
        mUserVM.mUser.observe(this) {
            toast(it.toString())
        }
    }


}