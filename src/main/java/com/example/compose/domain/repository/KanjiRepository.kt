package com.example.compose.domain.repository

import com.example.compose.domain.model.ExampleWord
import com.example.compose.domain.model.Kanji
import com.example.compose.domain.model.Word

import kotlinx.coroutines.flow.Flow

interface KanjiRepository {

    suspend fun addKanji(kanji: Kanji): Int

    fun getKanjiById(id: Int): Flow<Kanji?>

    fun getAllKanjis(): Flow<List<Kanji>>

    fun observeKanjiCount(): Flow<Int>

    fun observeWordsForKanji(kanjiId: Int): Flow<List<Word>>

    suspend fun updateKanji(kanji: Kanji)

    suspend fun deleteKanji(kanji: Kanji)

    suspend fun addExampleWord(
        kanjiId: Int,
        exampleWord: ExampleWord
    )

    suspend fun deleteExampleWord(
        kanjiId: Int,
        exampleWord: ExampleWord
    )
}
