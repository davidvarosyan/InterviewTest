package com.varosyan.data.repo

import com.varosyan.data.mapper.BetMapper
import com.varosyan.data.service.UpdateBetService
import com.varosyan.domain.common.safeCall
import com.varosyan.domain.model.Bet
import com.varosyan.domain.repo.UpdateBetRepo

class UpdateBetRepoImpl(
    private val updateBetService: UpdateBetService,
    private val mapper: BetMapper
) : UpdateBetRepo {
    override suspend fun invoke(bets: List<Bet>) = safeCall {
        updateBetService.updateBets(bets.map { mapper.mapFromDomain(it) })
    }
}