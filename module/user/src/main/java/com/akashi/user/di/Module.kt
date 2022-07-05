package com.akashi.user.di

import com.akashi.user.repo.UserRepo
import com.akashi.user.vm.UserVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val userModule = module {
    single { UserRepo(get()) }
    viewModel { UserVM(get()) }
}