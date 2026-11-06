package com.example.compose.domain.repository

import com.example.compose.domain.model.WordAPI

interface DictionaryRepository {
    suspend fun searchWords(query: String): List<WordAPI>
}