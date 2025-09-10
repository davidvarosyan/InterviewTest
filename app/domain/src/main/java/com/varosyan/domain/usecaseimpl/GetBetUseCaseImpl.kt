package com.varosyan.domain.usecaseimpl

import com.varosyan.domain.common.Result
import com.varosyan.domain.model.Bet
import com.varosyan.domain.repo.GetBetRepo
import com.varosyan.domain.usecase.GetBetsUseCase
import kotlinx.coroutines.delay

class GetBetUseCaseImpl(
    private val getBetRepo: GetBetRepo,
) : GetBetsUseCase {
    override suspend fun invoke(): Result<List<Bet>> {
        return getBetRepo()
    }
}