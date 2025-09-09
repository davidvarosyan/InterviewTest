package com.varosyan.domain.model

data class Bet(
    val betType: BetType,
    val sellIn: Int,
    val odds: Int,
    val imageURL: String
)