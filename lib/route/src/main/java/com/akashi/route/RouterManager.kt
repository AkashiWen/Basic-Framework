package com.akashi.route

import android.app.Application
import com.akashi.route.impl.ARouterProxy

/**
 * 静态代理模式调用路由组件
 */
class RouterManager private constructor(): IRouter {

    companion object {
        val instance by lazy {
            RouterManager()
        }
    }

    // TODO 改用 Koin inject 注入
    private var router: IRouter? = ARouterProxy()


    override fun init(application: Application) {
        router?.init(application)
    }

    override fun destroy() {
        router?.destroy()
    }

    override fun navigationTo(route: String) {
        router?.navigationTo(route)
    }

}