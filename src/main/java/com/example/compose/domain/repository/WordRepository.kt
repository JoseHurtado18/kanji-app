package com.example.compose.domain.repository

import com.example.compose.domain.model.Kanji
import com.example.compose.domain.model.Word
import kotlinx.coroutines.flow.Flow

interface WordRepository {
    fun observeAllWords(): Flow<List<Word>>

    suspend fun getWordById(id: Int): Word?

    fun observeWordById(id: Int): Flow<Word?>

    suspend fun addWord(word: Word): Int

    suspend fun updateWord(word: Word)

    fun getAllKanjisForWord(id: Int): Flow<List<Kanji>>
}
