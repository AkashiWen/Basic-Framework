package com.akashi.basicframework.model

interface IBaseModel<B> {

    fun fetch(listener: ModelLoaderListener<B>)

    interface ModelLoaderListener<B> {
        fun onComplete(bean: B)
        fun onError(msg: String)
    }

}