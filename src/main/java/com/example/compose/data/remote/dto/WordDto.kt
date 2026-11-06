package com.example.compose.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class WordDto(
    val wordId: Int,
    val mainReading: ReadingDto,
    val alternativeReadings: List<ReadingDto> = emptyList()
    // tu captura se corta aquí: puede haber más campos (definiciones, etc.)
)