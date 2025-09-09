package com.varosyan.domain.usecase

import com.varosyan.domain.model.Bet

interface CalculateOddsUseCase {
    suspend fun calculateOdds(bets: List<Bet>): List<Bet>
}