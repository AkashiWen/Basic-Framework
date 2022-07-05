package com.akashi.common.base.mvvm

import android.os.Bundle
import androidx.annotation.LayoutRes
import com.akashi.common.util.toast
import org.koin.androidx.scope.ScopeActivity

abstract class BaseActivity(@LayoutRes layoutId: Int) : ScopeActivity(layoutId) {

    abstract fun initView()
    abstract fun initViewModel()
    abstract fun getViewModels(): List<BaseViewModel>

    private val mLittleActionClientCallback by lazy {
        object : BaseViewModel.NoActionClientCallback() {
            override fun onApiStart() = showLoading()
            override fun onApiFinal() = finishLoading()
            override fun onApiEmpty() = showEmpty()
            override fun onApiError(e: Throwable?, toast: String?) {
                super.onApiError(e, toast)
                showError(toast)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initViewModel()

        registerClientCallbacks()
    }


    private fun registerClientCallbacks() {
        getViewModels().forEach {
            it.registerClientCallbackListener(mLittleActionClientCallback)
        }
    }

    protected open fun showLoading() {
        toast("start loading user..")
    }

    protected open fun finishLoading() {
        toast("loading finish..!!")
    }

    protected open fun showEmpty() {}
    protected open fun showError(err: String?) {
        toast(err)
    }

}