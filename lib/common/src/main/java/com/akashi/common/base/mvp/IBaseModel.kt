package com.akashi.common.base.mvp

interface IBaseModel<B> {

    fun fetch(listener: ModelLoaderListener<B>)

    interface ModelLoaderListener<B> {
        fun onComplete(bean: B)
        fun onError(msg: String)
    }

}