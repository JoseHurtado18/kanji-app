package com.example.compose.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ReadingDto(
    val text: String,                      // "学[がっ]校[こう]"
    val readingType: Int,
    val readingIndex: Int,
    val frequencyRank: Int? = null,
    val frequencyPercentage: Double? = null,
    val usedInMediaAmount: Int? = null,
    val usedInMediaAmountByType: Map<String, Int> = emptyMap()
)
