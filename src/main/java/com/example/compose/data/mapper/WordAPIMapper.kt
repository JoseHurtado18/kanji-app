package com.example.compose.data.mapper

import com.example.compose.data.remote.dto.SearchResultDto
import com.example.compose.domain.model.WordAPI

fun SearchResultDto.toDomain() = WordAPI(
    wordId = wordId,
    readingIndex = readingIndex,
    text = text,
    rubyText = rubyText
)