package com.akashi.basicframework

import com.akashi.opensource.di.opensourceModule
import com.akashi.payment.paymentModule
import com.akashi.user.di.userModule

val modules = listOf(
    userModule, paymentModule,
    opensourceModule
)