package com.example.compose.data.repository

import com.example.compose.data.dao.ExampleWordDAO
import com.example.compose.data.dao.KanjiDAO
import com.example.compose.data.entity.toDomain
import com.example.compose.data.mapper.toDomain
import com.example.compose.data.mapper.toEntity
import com.example.compose.domain.model.ExampleWord
import com.example.compose.domain.model.Kanji
import com.example.compose.domain.model.Word
import com.example.compose.domain.repository.KanjiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class KanjiRepositoryImpl @Inject constructor(
    private val kanjiDao: KanjiDAO,
    private val exampleWordDao: ExampleWordDAO
) : KanjiRepository {

    override suspend fun addKanji(kanji: Kanji): Int {
        return kanjiDao.insertKanji(kanji.toEntity()).toInt()
    }

    override fun getKanjiById(id: Int): Flow<Kanji?> {
        return kanjiDao.getKanjiWithExampleWords(id).map { kanjiWithWords ->
                kanjiWithWords?.toDomain()
            }
    }

    override fun getAllKanjis(): Flow<List<Kanji>> {
        return kanjiDao.getAllKanjisWithExampleWords().map { list ->
                list.map {
                    it.toDomain()
                }
            }
    }

    override fun observeKanjiCount(): Flow<Int> {
        return kanjiDao.observeKanjiCount()
    }

    override fun observeWordsForKanji(kanjiId: Int): Flow<List<Word>> {
        return kanjiDao.getKanjiWithWords(kanjiId).map { kanjiWithWords ->
            kanjiWithWords?.words?.map { it.toDomain() } ?: emptyList()
        }
    }

    override suspend fun updateKanji(kanji: Kanji) { kanjiDao.updateKanji(
            kanji.toEntity()
        )
    }

    override suspend fun deleteKanji(kanji: Kanji) { kanjiDao.deleteKanji(kanji.toEntity())
    }

    override suspend fun addExampleWord(
        kanjiId: Int,
        exampleWord: ExampleWord
    ) {

        exampleWordDao.insertExampleWord(
            exampleWord.toEntity(kanjiId)
        )
    }


    override suspend fun deleteExampleWord(
        kanjiId: Int,
        exampleWord: ExampleWord
    ) {

        exampleWordDao.deleteExampleWord(
            exampleWord.toEntity(kanjiId)
        )
    }
}