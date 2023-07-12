package com.akashi.common.base.api

/**
 * Response model for request
 */
data class AResponse<T>(val message: String = "", val code: Int = NO_CONTENT, val data: T?) {
    companion object {
        const val OK = 200
        const val NO_CONTENT = 204
        const val CLIENT_ERROR = 400
        const val UNAUTHORIZED = 401
        const val SERVER_ERROR = 500
    }

    fun isFail() = this.code >= CLIENT_ERROR

    fun isUnAuthorized() = this.code == UNAUTHORIZED

    fun isSuccess() = this.code == OK || this.code == NO_CONTENT

}

/**
 * Exception model for request
 */
data class ResultError(
    val e: Throwable? = null,
    var toast: String? = null
)