package com.example.compose.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class KanjiComponentDto(
    val character: String,
    val meanings: List<String> = emptyList(),
    val strokeCount: Int? = null,
    val jlptLevel: Int? = null,
    val grade: Int? = null,
    val frequencyRank: Int? = null
)