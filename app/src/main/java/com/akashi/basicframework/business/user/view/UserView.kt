package com.akashi.basicframework.business.user.view

import com.akashi.basicframework.business.user.bean.User
import com.akashi.common.base.mvp.IBaseView

interface UserView : IBaseView {
    fun displayProfile(user: User)
}