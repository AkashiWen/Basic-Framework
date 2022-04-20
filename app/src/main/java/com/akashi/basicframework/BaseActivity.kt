package com.akashi.basicframework

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.akashi.basicframework.view.IBaseView

/**
 * BaseActivity
 * 1. 泛型 Presenter、View
 * 2. 实现IBaseView 方便presenter初始化（attachView）
 */
abstract class BaseActivity<P : BasePresenter<V>, V : IBaseView> : AppCompatActivity(),
    IBaseView {

    abstract fun createPresenter(): P
    protected lateinit var presenter: P

    /**
     * 模板方法
     */
    abstract fun init()
    protected open fun register() {}
    protected open fun unRegister() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this as V

        this.presenter = createPresenter()

        this.presenter.let {
            // 连接当前Presenter和当前View
            it.attachView(this)
            // 添加观察者
            lifecycle.addObserver(it)
        }

        init()
        register()
    }

    override fun onDestroy() {
        super.onDestroy()
        // 基础父类统一处理泄漏问题
        this.presenter.detachView()
        // 注销
        unRegister()
    }
}