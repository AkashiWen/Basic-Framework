package com.akashi.common.base.mvvm

import android.accounts.NetworkErrorException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akashi.common.base.api.AResponse
import com.akashi.common.base.api.ResultError
import com.akashi.common.logger.logE
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

abstract class BaseViewModel : ViewModel() {

    interface ClientCallback {
        fun onApiStart()
        fun onApiSuccess()
        fun onApiEmpty()
        fun onApiError(e: Throwable?, toast: String?)
        fun onApiFinal()
    }

    abstract class NoActionClientCallback : ClientCallback {
        override fun onApiStart() {}
        override fun onApiSuccess() {}
        override fun onApiEmpty() {}
        override fun onApiError(e: Throwable?, toast: String?) {
            if (e == null || toast.isNullOrEmpty()) return
            logE(e, toast)
        }

        override fun onApiFinal() {}
    }

    private var mClientCallback: ClientCallback? = null

    /**
     * Activity需要处理Loading等状态时，注册这个接口
     */
    fun registerClientCallbackListener(clientCallback: ClientCallback) {
        this.mClientCallback = clientCallback
    }

    /**
     * Main entrance for network request
     */
    protected fun <T> apiLaunch(viewModelDsl: ViewModelDSL<T>.() -> Unit): Job {
        return handleRequestViewModelDsl(
            ViewModelDSL<T>().apply(viewModelDsl)
        ).let {
            performLaunch(it)
        }
    }

    /**
     * real perform launch
     */
    private fun <T> performLaunch(viewModelDsl: ViewModelDSL<T>.() -> Unit) =
        ViewModelDSL<T>().apply(viewModelDsl).launch()


    /**
     * handle dsl from client come in
     * isHandled:
     * true -> 在真实的ViewModelDsl中手动处理（优先），便不再走ClientCallback回调
     * false -> 走ClientCallback回调
     */
    private fun <T> handleRequestViewModelDsl(viewModelDsl: ViewModelDSL<T>): ViewModelDSL<T>.() -> Unit =
        {
            onStart {
                val isHandled = viewModelDsl.onStart?.invoke()
                if (isHandled != true) {
                    mClientCallback?.onApiStart()
                }
                return@onStart isHandled
            }
            onRequest {
                viewModelDsl.onRequest.invoke()
            }
            onResponseEmpty {
                val isHandled = viewModelDsl.onResponseEmpty?.invoke()
                if (isHandled != true) {
                    mClientCallback?.onApiEmpty()
                }
                return@onResponseEmpty isHandled
            }
            onResponse {
                mClientCallback?.onApiSuccess()
                viewModelDsl.onResponse?.invoke(it)
            }
            onError {
                val isHandled = viewModelDsl.onError?.invoke(it)
                if (isHandled != true) {
                    mClientCallback?.onApiError(it.e, it.toast)
                }
                return@onError isHandled
            }
            onFinally {
                val isHandled = viewModelDsl.onFinally?.invoke()
                if (isHandled != true) {
                    mClientCallback?.onApiFinal()
                }
                return@onFinally isHandled
            }
        }

    /**
     * ViewModel dsl
     */
    inner class ViewModelDSL<T> {

        private val timeout = 10L * 1000

        internal var onStart: (suspend () -> Boolean?)? = null
        internal lateinit var onRequest: (suspend () -> AResponse<T>)
        internal var onResponse: (suspend (AResponse<T>) -> Unit)? = null
        internal var onResponseEmpty: (suspend () -> Boolean?)? = null
        internal var onError: (suspend (ResultError) -> Boolean?)? = null
        internal var onFinally: (suspend () -> Boolean?)? = null

        fun onStart(onStart: (suspend () -> Boolean?)) {
            this.onStart = onStart
        }

        fun onRequest(request: suspend () -> AResponse<T>) {
            this.onRequest = request
        }

        fun onResponse(onResponse: suspend (AResponse<T>) -> Unit) {
            this.onResponse = onResponse
        }

        fun onResponseEmpty(onResponseEmpty: (suspend () -> Boolean?)?) {
            this.onResponseEmpty = onResponseEmpty
        }

        fun onError(onError: (suspend (ResultError) -> Boolean?)?) {
            this.onError = onError
        }

        fun onFinally(onFinally: (suspend () -> Boolean?)?) {
            this.onFinally = onFinally
        }

        /**
         * start
         */
        fun launch() = viewModelScope.launch {
            onStart?.invoke()
            try {
                withTimeout(timeout) {
                    val result = onRequest.invoke()
                    if (isEmpty(result)) {
                        onResponseEmpty?.invoke()
                    } else {
                        callback(result)
                    }
                }
            } catch (e: Throwable) {
                callbackException(e)
            } finally {
                onFinally?.invoke()
            }
        }

        private suspend fun callback(response: AResponse<T>?) {
            if (response == null || response.isFail()) {
                val errorMessage =
                    "Client or Server error: ${response?.code} / ${response?.message}"
                callbackException(
                    e = NetworkErrorException(errorMessage),
                    toast = response?.message
                )
            } else {
                onResponse?.invoke(response)
            }
        }

        private suspend fun callbackException(e: Throwable, toast: String? = null) {
            onError?.invoke(ResultError(e, toast))
        }

        private fun isEmpty(aResponse: AResponse<T>?): Boolean {
            val responseData = aResponse?.data
            return aResponse == null || responseData == null ||
                    (responseData is List<*> && responseData.isEmpty()) ||
                    aResponse.code == AResponse.NO_CONTENT
        }
    }


}