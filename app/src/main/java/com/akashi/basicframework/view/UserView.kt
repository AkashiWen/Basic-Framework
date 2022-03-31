package com.akashi.basicframework.view

import com.akashi.basicframework.bean.User

interface UserView : IBaseView {
    fun displayProfile(user: User)
}