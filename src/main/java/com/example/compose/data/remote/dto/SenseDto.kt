package com.example.compose.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class SenseDto(
    val index: Int,
    val meanings: List<String> = emptyList(),
    val partsOfSpeech: List<String> = emptyList(),
    val misc: List<String> = emptyList()
)