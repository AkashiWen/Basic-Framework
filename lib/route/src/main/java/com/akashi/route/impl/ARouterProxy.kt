package com.akashi.route.impl

import android.app.Application
import com.akashi.route.BuildConfig
import com.akashi.route.IRouter
import com.alibaba.android.arouter.launcher.ARouter

/**
 * ARouter代理
 */
class ARouterProxy : IRouter {

    override fun init(application: Application) {
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(application)
    }

    override fun destroy() {
        ARouter.getInstance().destroy()
    }

    override fun navigationTo(route: String) {
        ARouter.getInstance().build(route).navigation()
    }
}