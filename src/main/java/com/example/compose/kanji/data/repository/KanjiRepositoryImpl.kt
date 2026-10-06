package com.example.compose.kanji.data.repository

import com.example.compose.kanji.data.dao.ExampleWordDAO
import com.example.compose.kanji.data.dao.KanjiDAO
import com.example.compose.kanji.data.entity.KanjiEntity
import com.example.compose.kanji.data.entity.toDomain
import com.example.compose.kanji.data.mapper.toEntity
import com.example.compose.kanji.domain.model.ExampleWord
import com.example.compose.kanji.domain.model.Kanji
import com.example.compose.kanji.domain.repository.KanjiRepository
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