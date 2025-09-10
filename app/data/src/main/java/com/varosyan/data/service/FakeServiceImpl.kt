package com.varosyan.data.service

import com.varosyan.data.model.BetModel
import kotlinx.coroutines.delay

class FakeServiceImpl : GetBetService, UpdateBetService {

    override suspend fun getBets(): List<BetModel> {
        delay(500)
        return fakeBets
    }

    override suspend fun updateBets(bets: List<BetModel>) {
        fakeBets.apply {
            clear()
            addAll(bets)
        }
    }

    companion object {
        val fakeBets: MutableList<BetModel> = mutableListOf(
            BetModel("Winning team", 10, 20, "https://i.imgur.com/mx66SBD.jpeg"),
            BetModel("Total score", 2, 0, "https://i.imgur.com/VnPRqcv.jpeg"),
            BetModel("Player performance", 5, 7, "https://i.imgur.com/Urpc00H.jpeg"),
            BetModel("First goal scorer", 0, 80, "https://i.imgur.com/Wy94Tt7.jpeg"),
            BetModel("Number of fouls", 5, 49, "https://i.imgur.com/NMLpcKj.jpeg"),
            BetModel("Corner kicks", 3, 6, "https://i.imgur.com/TiJ8y5l.jpeg")
        )
    }
}