package com.varosyan.connector.koin

import com.varosyan.domain.usecase.CalculateOddsUseCase
import com.varosyan.domain.usecase.GetBetsUseCase
import com.varosyan.domain.usecase.UpdateBetsUseCase
import com.varosyan.domain.usecaseimpl.CalculateOddsUseCaseImpl
import com.varosyan.domain.usecaseimpl.GetBetUseCaseImpl
import com.varosyan.domain.usecaseimpl.UpdateBetsUseCaseImpl
import org.koin.dsl.module

fun domainModule() = module {
    factory<GetBetsUseCase> { GetBetUseCaseImpl(get()) }
    factory<CalculateOddsUseCase> { CalculateOddsUseCaseImpl() }
    factory<UpdateBetsUseCase> { UpdateBetsUseCaseImpl(get(), get(), get()) }
}