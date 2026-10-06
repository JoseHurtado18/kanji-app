package com.example.compose.kanji.domain.repository

import com.example.compose.kanji.domain.model.ExampleWord
import com.example.compose.kanji.domain.model.Kanji
import kotlinx.coroutines.flow.Flow

interface KanjiRepository {

    suspend fun addKanji(kanji: Kanji): Int

    fun getKanjiById(id: Int): Flow<Kanji?>

    fun getAllKanjis(): Flow<List<Kanji>>

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
