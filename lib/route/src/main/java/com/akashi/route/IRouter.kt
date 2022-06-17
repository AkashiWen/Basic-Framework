package com.akashi.route

import android.app.Application

interface IRouter {

    fun init(application: Application)

    fun destroy()

    fun navigationTo(route: String)
}