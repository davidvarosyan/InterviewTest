package com.varosyan.data.model

data class BetModel(
    val betType: String,
    val sellIn: Int,
    val odds: Int,
    val imageURL: String? = null
)