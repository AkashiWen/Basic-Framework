package com.akashi.basicframework.view

import com.akashi.basicframework.business.user.bean.User

interface UserView : IBaseView {
    fun displayProfile(user: User)
}