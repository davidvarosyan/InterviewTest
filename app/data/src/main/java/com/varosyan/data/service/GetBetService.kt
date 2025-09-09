package com.varosyan.data.service

import com.varosyan.data.model.BetModel

interface GetBetService {
    suspend fun getBets():List<BetModel>
}