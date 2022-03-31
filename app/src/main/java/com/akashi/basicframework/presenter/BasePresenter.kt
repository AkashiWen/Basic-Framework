package com.akashi.basicframework.presenter

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.akashi.basicframework.view.IBaseView
import java.lang.ref.WeakReference

/**
 * BasePresenter
 * enhance:
 * 1. 成为LifecycleObserver观察者，感知Activity/Fragment生命周期
 * 2. 使用OnLifecycleEvent注解回调
 */
open class BasePresenter<V : IBaseView> : LifecycleObserver {

    protected var view: WeakReference<V>? = null

    fun attachView(view: V) {
        this.view = WeakReference(view)
    }

    fun detachView() {
        view?.clear()
        view = null
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    protected open fun onCreate(owner: LifecycleOwner) {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    protected open fun onStart(owner: LifecycleOwner) {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    protected open fun onPause(owner: LifecycleOwner) {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    protected open fun onStop(owner: LifecycleOwner) {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    protected open fun onResume(owner: LifecycleOwner) {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    protected open fun onDestroy(owner: LifecycleOwner) {
    }
}