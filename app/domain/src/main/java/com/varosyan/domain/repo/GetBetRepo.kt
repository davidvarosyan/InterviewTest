package com.varosyan.domain.repo

import com.varosyan.domain.common.Result
import com.varosyan.domain.model.Bet

interface GetBetRepo {
    suspend operator fun invoke(): Result<List<Bet>>
}