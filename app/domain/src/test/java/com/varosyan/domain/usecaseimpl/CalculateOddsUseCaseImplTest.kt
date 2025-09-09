package com.varosyan.domain.usecaseimpl

import com.varosyan.domain.model.Bet
import com.varosyan.domain.model.BetType
import com.varosyan.domain.usecase.CalculateOddsUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test


class CalculateOddsUseCaseImplTest {

    private val useCase: CalculateOddsUseCase = CalculateOddsUseCaseImpl()

    /* ---------- Helpers ---------- */

    private fun singleResult(bet: Bet): Bet = runBlocking {
        useCase.calculateOdds(listOf(bet)).single()
    }

    /* ---------- Regular decay types: WINNING_TEAM, CORNER_KICKS ---------- */

    @Test
    fun winningTeam_decaysBy1_and_sellInMinus1() {
        val result = singleResult(Bet(BetType.WINNING_TEAM, sellIn = 5, odds = 10))
        assertEquals(4, result.sellIn)
        assertEquals(9, result.odds)
    }

    @Test
    fun winningTeam_doubleDecay_afterExpiry() {
        val result = singleResult(Bet(BetType.WINNING_TEAM, sellIn = -1, odds = 10))
        // dec 1 -> 9, sellIn -2; since sellIn < 0, extra dec 1 -> 8
        assertEquals(-2, result.sellIn)
        assertEquals(8, result.odds)
    }

    @Test
    fun cornerKicks_behavesLikeRegularDecay() {
        val result = singleResult(Bet(BetType.CORNER_KICKS, sellIn = 0, odds = 2))
        // dec 1 -> 1, sellIn -1; since sellIn < 0, extra dec 1 -> 0
        assertEquals(-1, result.sellIn)
        assertEquals(0, result.odds)
    }

    @Test
    fun regularDecay_neverGoesBelowZero() {
        val result = singleResult(Bet(BetType.WINNING_TEAM, sellIn = -5, odds = 1))
        // dec 1 -> 0, extra dec skipped because already 0
        assertEquals(-6, result.sellIn)
        assertEquals(0, result.odds)
    }

    /* ---------- TOTAL_SCORE (increases; faster after expiry) ---------- */

    @Test
    fun totalScore_increaseBy1_ifBelow50() {
        val result = singleResult(Bet(BetType.TOTAL_SCORE, sellIn = 10, odds = 49))
        assertEquals(9, result.sellIn)
        assertEquals(50, result.odds)
    }

    @Test
    fun totalScore_doesNotClampDown_ifStartingAbove50() {
        val result = singleResult(Bet(BetType.TOTAL_SCORE, sellIn = 10, odds = 200))
        // Only increases when < 50; 200 stays 200
        assertEquals(9, result.sellIn)
        assertEquals(200, result.odds)
    }

    @Test
    fun totalScore_increaseBy2_afterExpiry_usesUpdatedSellIn() {
        // NOTE: This assumes you fixed calculateTotalScoreOdd to check `if (sellIn < 0)` after decrement.
        val result = singleResult(Bet(BetType.TOTAL_SCORE, sellIn = 0, odds = 10))
        // +1 -> 11, sellIn: 0 -> -1, since <0, +1 again -> 12
        assertEquals(-1, result.sellIn)
        assertEquals(12, result.odds)
    }

    @Test
    fun totalScore_neverExceeds50_evenAfterExpiry() {
        val result = singleResult(Bet(BetType.TOTAL_SCORE, sellIn = -1, odds = 49))
        // +1 -> 50, sellIn -2; second +1 skipped because not < 50
        assertEquals(-2, result.sellIn)
        assertEquals(50, result.odds)
    }

    /* ---------- NUMBER_OF_FOULS (accelerates; drops to 0 after expiry) ---------- */

    @Test
    fun fouls_baselinePlus1_whenSellInAtLeast11() {
        val result = singleResult(Bet(BetType.NUMBER_OF_FOULS, sellIn = 11, odds = 10))
        assertEquals(10, result.sellIn)
        assertEquals(11, result.odds)
    }

    @Test
    fun fouls_plus2_whenSellIn10to6() {
        val result = singleResult(Bet(BetType.NUMBER_OF_FOULS, sellIn = 10, odds = 10))
        // +1 base, +1 (<11), not <6 yet => total +2
        assertEquals(9, result.sellIn)
        assertEquals(12, result.odds)
    }

    @Test
    fun fouls_plus3_whenSellIn5to0() {
        val result = singleResult(Bet(BetType.NUMBER_OF_FOULS, sellIn = 5, odds = 10))
        // +1 base, +1 (<11), +1 (<6) => total +3
        assertEquals(4, result.sellIn)
        assertEquals(13, result.odds)
    }

    @Test
    fun fouls_capsAt50_stepByStep() {
        val result = singleResult(Bet(BetType.NUMBER_OF_FOULS, sellIn = 5, odds = 49))
        // +1 -> 50, next bumps skipped due to <50 guard
        assertEquals(4, result.sellIn)
        assertEquals(50, result.odds)
    }

    @Test
    fun fouls_dropsToZero_afterExpiry() {
        val result = singleResult(Bet(BetType.NUMBER_OF_FOULS, sellIn = -1, odds = 37))
        assertEquals(-2, result.sellIn)
        assertEquals(0, result.odds)
    }

    /* ---------- FIRST_GOAL_SCORER & PLAYER_PERFORMANCE (unchanged) ---------- */

    @Test
    fun firstGoalScorer_unchanged() {
        val result = singleResult(Bet(BetType.FIRST_GOAL_SCORER, sellIn = 7, odds = 200))
        assertEquals(7, result.sellIn)
        assertEquals(200, result.odds)
    }

    @Test
    fun playerPerformance_unchanged() {
        val result = singleResult(Bet(BetType.PLAYER_PERFORMANCE, sellIn = -3, odds = 15))
        assertEquals(-3, result.sellIn)
        assertEquals(15, result.odds)
    }

    /* ---------- List mapping behavior ---------- */

    @Test
    fun calculateOdds_mapsList_andAppliesRules() = runBlocking {
        val bets = listOf(
            Bet(BetType.WINNING_TEAM, sellIn = 1, odds = 1),         // -> sellIn 0, odds 0
            Bet(BetType.TOTAL_SCORE, sellIn = 2, odds = 49),         // -> sellIn 1, odds 50
            Bet(BetType.NUMBER_OF_FOULS, sellIn = 6, odds = 10),     // -> sellIn 5, odds 12
            Bet(BetType.FIRST_GOAL_SCORER, sellIn = 3, odds = 99)    // unchanged
        )

        val result = useCase.calculateOdds(bets)

        assertEquals(0, result[0].sellIn);  assertEquals(0,  result[0].odds)
        assertEquals(1, result[1].sellIn);  assertEquals(50, result[1].odds)
        assertEquals(5, result[2].sellIn);  assertEquals(12, result[2].odds)
        assertEquals(3, result[3].sellIn);  assertEquals(99, result[3].odds)
    }
}