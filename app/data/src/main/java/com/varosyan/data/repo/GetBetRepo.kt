package com.varosyan.data.repo

import com.varosyan.data.mapper.BetMapper
import com.varosyan.data.service.GetBetService
import com.varosyan.domain.common.Result
import com.varosyan.domain.common.safeCall
import com.varosyan.domain.model.Bet
import com.varosyan.domain.repo.GetBetRepo

class GetBetRepoImpl(private val getBetService: GetBetService, private val mapper: BetMapper) :
    GetBetRepo {
    override suspend fun invoke(): Result<List<Bet>> = safeCall {
        getBetService.getBets().map { mapper.mapToDomain(it) }
    }
}