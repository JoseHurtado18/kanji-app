package com.example.compose.data.repository

import com.example.compose.data.mapper.toDomain
import com.example.compose.data.remote.api.JitenApi
import com.example.compose.domain.model.WordAPI
import com.example.compose.domain.repository.DictionaryRepository
import javax.inject.Inject

class DictionaryRepositoryImpl @Inject constructor(
    private val api: JitenApi
) : DictionaryRepository {

    override suspend fun searchWords(query: String): List<WordAPI> =
        api.searchVocabulary(query).results.map { it.toDomain() }
}