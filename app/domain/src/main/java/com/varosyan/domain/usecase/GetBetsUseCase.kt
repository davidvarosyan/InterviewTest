package com.varosyan.domain.usecase

import com.varosyan.domain.common.Result
import com.varosyan.domain.model.Bet

interface GetBetsUseCase {
    suspend operator fun invoke(): Result<List<Bet>>
}