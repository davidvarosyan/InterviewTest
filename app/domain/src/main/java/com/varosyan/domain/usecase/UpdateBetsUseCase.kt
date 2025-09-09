package com.varosyan.domain.usecase

import com.varosyan.domain.common.Result

interface UpdateBetsUseCase {
    suspend operator fun invoke(): Result<Unit>
}