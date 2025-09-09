package com.varosyan.data.mapper

import com.varosyan.data.model.BetModel
import com.varosyan.domain.model.Bet
import com.varosyan.domain.model.BetType

class BetMapper {
    fun mapToDomain(betModel: BetModel): Bet {
        val betType = when (betModel.betType) {
            TYPE_WINNING_TEAM -> BetType.WINNING_TEAM
            TYPE_TOTAL_SCORE -> BetType.TOTAL_SCORE
            TYPE_PLAYER_PERFORMANCE -> BetType.PLAYER_PERFORMANCE
            TYPE_FIRST_GOAL_SCORER -> BetType.FIRST_GOAL_SCORER
            TYPE_NUMBER_OF_FOULS -> BetType.NUMBER_OF_FOULS
            TYPE_CORNER_KICKS -> BetType.NUMBER_OF_FOULS
            else -> throw RuntimeException("Unknown type")
        }
        return Bet(
            betType,
            sellIn = betModel.sellIn,
            odds = betModel.odds,
            imageURL = betModel.imageURL
        )
    }

    fun mapFromDomain(bet: Bet): BetModel {
        val betType = when (bet.betType) {
            BetType.WINNING_TEAM -> TYPE_WINNING_TEAM
            BetType.TOTAL_SCORE -> TYPE_TOTAL_SCORE
            BetType.PLAYER_PERFORMANCE -> TYPE_PLAYER_PERFORMANCE
            BetType.FIRST_GOAL_SCORER -> TYPE_FIRST_GOAL_SCORER
            BetType.NUMBER_OF_FOULS -> TYPE_NUMBER_OF_FOULS
            BetType.CORNER_KICKS -> TYPE_CORNER_KICKS
        }
        return BetModel(betType, sellIn = bet.sellIn, odds = bet.sellIn, imageURL = bet.imageURL)
    }

    companion object {
        const val TYPE_WINNING_TEAM = "Winning team"
        const val TYPE_TOTAL_SCORE = "Total score"
        const val TYPE_PLAYER_PERFORMANCE = "Player performance"
        const val TYPE_FIRST_GOAL_SCORER = "First goal scorer"
        const val TYPE_NUMBER_OF_FOULS = "Number of fouls"
        const val TYPE_CORNER_KICKS = "Corner kicks"
    }
}