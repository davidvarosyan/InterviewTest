package com.varosyan.data.service

import com.varosyan.data.model.BetModel

interface UpdateBetService {
    suspend fun updateBets(bets:List<BetModel>)
}