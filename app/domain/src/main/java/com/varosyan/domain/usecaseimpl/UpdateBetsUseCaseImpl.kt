package com.varosyan.domain.usecaseimpl

import com.varosyan.domain.common.Result
import com.varosyan.domain.common.getOrNull

]import com.varosyan.domain.repo.UpdateBetRepo
import com.varosyan.domain.usecase.CalculateOddsUseCase
import com.varosyan.domain.usecase.GetBetsUseCase
import com.varosyan.domain.usecase.UpdateBetsUseCase

class UpdateBetsUseCaseImpl(
    private val updateBetRepo: UpdateBetRepo,
    private val getBetUseCase: GetBetsUseCase,
    private val calculateOddsUseCase: CalculateOddsUseCase,
) : UpdateBetsUseCase {
    override suspend fun invoke(): Result<Unit> {
        return updateBetRepo.invoke(
            calculateOddsUseCase(getBetUseCase.invoke().getOrNull() ?: throw RuntimeException())
        )
    }

}