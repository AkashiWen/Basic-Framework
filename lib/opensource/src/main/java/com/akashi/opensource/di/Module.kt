package com.akashi.opensource.di

import com.akashi.opensource.network.api.MeApi
import com.akashi.opensource.network.okHttpClient
import com.akashi.opensource.network.retrofit
import org.koin.dsl.module
import retrofit2.Retrofit


val opensourceModule = module {
    single { okHttpClient }
    single { retrofit }

    single { get<Retrofit>().create(MeApi::class.java) }
}