package com.varosyan.domain.usecase

import com.varosyan.domain.model.Bet

interface CalculateOddsUseCase {
    suspend operator fun invoke(bets: List<Bet>): List<Bet>
}