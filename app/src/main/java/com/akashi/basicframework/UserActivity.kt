package com.akashi.basicframework

import android.util.Log
import com.akashi.basicframework.bean.User
import com.akashi.basicframework.presenter.UserPresenter
import com.akashi.basicframework.view.UserView

class UserActivity : BaseActivity<UserPresenter, UserView>(), UserView {

    override fun createPresenter(): UserPresenter = UserPresenter()

    override fun init() {
        // 请求个人资料数据
        presenter.fetchProfile()
    }

    override fun displayProfile(user: User) {
        // TODO ..
        Log.i("displayProfile", user.toString())
    }

    override fun showError(msg: String) {
    }

}