package com.varosyan.domain.usecaseimpl

import com.varosyan.domain.model.Bet
import com.varosyan.domain.model.BetType
import com.varosyan.domain.usecase.CalculateOddsUseCase

class CalculateOddsUseCaseImpl : CalculateOddsUseCase {
    override suspend fun calculateOdds(bets: List<Bet>): List<Bet> {
        return bets.map {
            calculateOdds(it)
        }
    }

    private fun calculateOdds(bet: Bet): Bet {
        return when (bet.betType) {
            BetType.TOTAL_SCORE -> calculateTotalScoreOdd(bet)
            BetType.NUMBER_OF_FOULS -> calculateNumberOfFoulsOdd(bet)
            BetType.FIRST_GOAL_SCORER -> calculateFirstGoalScorerOdd(bet)
            BetType.WINNING_TEAM -> calculateWinningTeamOdd(bet)
            BetType.PLAYER_PERFORMANCE -> calculatePlayerPerformanceOdd(bet)
            BetType.CORNER_KICKS -> calculateCornerKicksOdd(bet)
        }
    }

    private fun calculateTotalScoreOdd(bet: Bet): Bet {
        var odds = bet.odds
        var sellIn = bet.sellIn
        odds = incIfBelowMax(odds, 1)             // improves over time
        sellIn -= 1
        if (sellIn < 0) odds = incIfBelowMax(odds, 1)
        return bet.copy(sellIn = sellIn, odds = odds)
    }

    private fun calculateWinningTeamOdd(bet: Bet): Bet {
        return calculateRegularOdd(bet)
    }

    private fun calculateFirstGoalScorerOdd(bet: Bet): Bet {
        return bet.copy()
    }

    private fun calculateNumberOfFoulsOdd(bet: Bet): Bet {
        var odds = bet.odds
        var sellIn = bet.sellIn
        odds = incIfBelowMax(odds, 1)
        if (sellIn < 11) odds = incIfBelowMax(odds, 1)
        if (sellIn < 6) odds = incIfBelowMax(odds, 1)

        sellIn -= 1

        if (sellIn < 0) odds = ODDS_MIN

        return bet.copy(sellIn = sellIn, odds = odds)
    }

    private fun calculateCornerKicksOdd(bet: Bet): Bet {
        return calculateRegularOdd(bet)
    }

    private fun calculatePlayerPerformanceOdd(bet: Bet): Bet {
        return calculateFirstGoalScorerOdd(bet)
    }

    private fun calculateRegularOdd(bet: Bet): Bet {
        var sellIn = bet.sellIn
        var odds = bet.odds
        odds = decIfUnderMin(odds, 1)
        sellIn -= 1
        if (sellIn < 0) odds = decIfUnderMin(odds, 1)
        return bet.copy(sellIn = sellIn, odds = odds)
    }


    private fun decIfUnderMin(odds: Int, count: Int): Int {
        var result = odds
        if (odds > ODDS_MIN) {
            result = (odds - count)
        }
        return result
    }

    private fun incIfBelowMax(odds: Int, count: Int): Int {
        var result = odds
        if (odds < ODDS_MAX) {
            result = odds + count
        }
        return result
    }

    companion object {
        private const val ODDS_MIN = 0
        private const val ODDS_MAX = 50
    }
}