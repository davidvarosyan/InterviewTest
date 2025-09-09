package com.varosyan.domain.repo

import com.varosyan.domain.common.Result
import com.varosyan.domain.model.Bet

interface UpdateBetRepo {
    suspend operator fun invoke(bets: List<Bet>): Result<Unit>
}