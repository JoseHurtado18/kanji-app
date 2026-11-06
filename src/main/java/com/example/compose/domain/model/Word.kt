package com.example.compose.domain.model

data class Word(
    val id: Int = 0,
    val kanjiWord: String,
    val reading: String,
    val meaning: String,
    val typeWord: TypeWord?,
    val sentence: String? = null,
    val notes: String? = null,
    val isDueForReview: Boolean = false
)