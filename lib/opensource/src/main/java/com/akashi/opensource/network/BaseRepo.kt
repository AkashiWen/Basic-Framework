package com.akashi.opensource.network

import com.akashi.common.base.api.AResponse
import retrofit2.Response

open class BaseRepo {

    fun <I, O : BaseClientBean<I, O>> convertToAResponse(
        response: Response<I>,
        block: () -> O
    ): AResponse<O> {
        return AResponse(
            message = response.message(),
            code = response.code(),
            data = block.invoke()
        )
    }
}