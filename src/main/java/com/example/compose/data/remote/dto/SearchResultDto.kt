package com.example.compose.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class SearchResultDto(
    val wordId: Int,
    val readingIndex: Int,
    val text: String,
    val rubyText: String,
    val primaryKanjiText: String? = null,
    val partsOfSpeech: List<String> = emptyList(),
    val meanings: List<String> = emptyList(),
    val senses: List<SenseDto> = emptyList(),
    val frequencyRank: Int? = null
)