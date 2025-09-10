package com.varosyan.connector.koin

import com.varosyan.presenter.viewmodel.BetViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

fun presenterModule() = module {
    viewModel {
        BetViewModel(get(),get ())
    }
}