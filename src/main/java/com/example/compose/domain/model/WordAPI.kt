package com.example.compose.domain.model

data class WordAPI(
    val wordId: Int,
    val readingIndex: Int,
    val text: String,
    val rubyText: String
)