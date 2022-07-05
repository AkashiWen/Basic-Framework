package com.akashi.user

import com.akashi.user.vm.UserVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val userModule = module {
    viewModel { UserVM() }
}