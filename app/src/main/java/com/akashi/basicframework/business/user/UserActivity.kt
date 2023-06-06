package com.akashi.basicframework.business.user

import android.content.res.Configuration
import android.util.Log
import android.widget.Toast
import com.akashi.basicframework.business.user.bean.User
import com.akashi.basicframework.business.user.presenter.UserPresenter
import com.akashi.basicframework.business.user.view.UserView
import com.akashi.common.base.mvp.BaseActivity
import com.akashi.common.logger.logI

class UserActivity : BaseActivity<UserPresenter, UserView>(), UserView {

    override fun createPresenter(): UserPresenter = UserPresenter()

    override fun init() {
        // 请求个人资料数据
        presenter.fetchProfile()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        logI("UserActivity ---- onConfigurationChanged: ${newConfig.orientation}")
    }

    override fun displayProfile(user: User) {
        Log.i("displayProfile", user.toString())
        Toast.makeText(this, "$user", Toast.LENGTH_SHORT).show()
    }

    override fun showError(msg: String) {
    }

}