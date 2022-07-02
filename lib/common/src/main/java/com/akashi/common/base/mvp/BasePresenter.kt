package com.akashi.common.base.mvp

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.akashi.common.logger.logI
import com.akashi.common.util.now
import java.lang.ref.WeakReference

/**
 * BasePresenter
 * enhance:
 * 1. 成为LifecycleObserver观察者，感知Activity/Fragment生命周期
 * 2. 使用OnLifecycleEvent注解回调
 */
open class BasePresenter<V : IBaseView> : LifecycleObserver {

    protected var view: WeakReference<V>? = null

    private val mEventObserver by lazy {
        LifecycleEventObserver { owner, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> {
                    onCreate(owner)
                }
                Lifecycle.Event.ON_DESTROY -> {
                    onDestroy(owner)
                }
                else -> {}
            }
        }
    }

    fun attachOwner(owner: LifecycleOwner) {
        owner.lifecycle.addObserver(mEventObserver)
    }

    fun detachOwner(owner: LifecycleOwner) {
        owner.lifecycle.removeObserver(mEventObserver)
    }

    fun attachView(view: V) {
        this.view = WeakReference(view)
    }

    fun detachView() {
        view?.clear()
        view = null
    }

    protected open fun onCreate(owner: LifecycleOwner) {
        logI("Presenter onCreate: ---> ${now()}")
    }

    protected open fun onDestroy(owner: LifecycleOwner) {
        logI("Presenter onDestroy: ---> ${now()}")
    }

}