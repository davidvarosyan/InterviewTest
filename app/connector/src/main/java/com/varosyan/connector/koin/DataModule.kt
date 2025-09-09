package com.varosyan.connector.koin

import com.varosyan.data.mapper.BetMapper
import com.varosyan.data.repo.GetBetRepoImpl
import com.varosyan.data.repo.UpdateBetRepoImpl
import com.varosyan.data.service.FakeServiceImpl
import com.varosyan.data.service.GetBetService
import com.varosyan.data.service.UpdateBetService
import com.varosyan.domain.repo.GetBetRepo
import com.varosyan.domain.repo.UpdateBetRepo
import org.koin.dsl.module

 fun dataModule() = module {
    single<FakeServiceImpl> { FakeServiceImpl() }
    single<GetBetService> { get<FakeServiceImpl>() }
    single<UpdateBetService> { get<FakeServiceImpl>() }
    factory<GetBetRepo> { GetBetRepoImpl(get(), get()) }
    factory<UpdateBetRepo> { UpdateBetRepoImpl(get(), get()) }
    factory<BetMapper> { BetMapper() }
}