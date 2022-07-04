package com.akashi.common.base.mvvm

import android.os.Bundle
import org.koin.androidx.scope.ScopeActivity

abstract class BaseActivity : ScopeActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

}