package com.akashi.opensource.network

interface BaseClientBean<ApiModel, clientBean> {
    /**
     * api數據模型 轉換 -> 本地數據模型
     */
    fun convert(apiModel: ApiModel?): clientBean
}