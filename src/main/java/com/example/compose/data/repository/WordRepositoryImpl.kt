package com.example.compose.data.repository

import com.example.compose.data.dao.WordDAO
import com.example.compose.data.dao.WordLearningStateDao
import com.example.compose.data.dao.WordReadingDAO
import com.example.compose.data.entity.toDomain
import com.example.compose.data.mapper.toDomain
import com.example.compose.data.mapper.toEntity
import com.example.compose.domain.model.Kanji
import com.example.compose.domain.model.KanjiReadingAPI
import com.example.compose.domain.model.Word
import com.example.compose.domain.repository.WordRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WordRepositoryImpl @Inject constructor(
    private val wordDao: WordDAO,
    private val wordReadingDao: WordReadingDAO,
    private val learningStateDao: WordLearningStateDao
): WordRepository {

    override fun getAllKanjisForWord(id: Int): Flow<List<Kanji>> {
        return wordDao.getKanjisForWord(id).map { list ->
            list.map {
                it.toDomain()
            }
        }
    }

    override fun observeAllWords(): Flow<List<Word>> {
        return combine(
            wordDao.observeAllWordsWithReadings(),
            learningStateDao.observeDueWords(System.currentTimeMillis())
        ) { wordEntities, dueStates ->
            val dueWordIds = dueStates.map { it.wordId }.toSet()
            wordEntities.map { entityWithReadings ->
                entityWithReadings.toDomain().copy(
                    isDueForReview = entityWithReadings.word.id in dueWordIds
                )
            }
        }
    }

    override suspend fun getWordById(id: Int): Word? {
        return wordDao.getWordWithReadingsById(id)?.toDomain()
    }

    override fun observeWordById(id: Int): Flow<Word?> {
        return wordDao.observeWordWithReadingsById(id).map { it?.toDomain() }
    }

    override suspend fun addWord(word: Word): Int {
        val wordId = wordDao.insertWord(word.toEntity()).toInt()
        val validReadings = word.readings.filter { it.kanji.isNotBlank() || it.reading.isNotBlank() }
        if (validReadings.isNotEmpty()) {
            val entities = validReadings.map { it.toEntity(wordId) }
            wordReadingDao.insertWordReadings(entities)
        }
        return wordId
    }

    override suspend fun updateWord(word: Word) {
        wordDao.updateWord(word.toEntity())
        wordReadingDao.deleteReadingsForWord(word.id)
        val validReadings = word.readings.filter { it.kanji.isNotBlank() || it.reading.isNotBlank() }
        if (validReadings.isNotEmpty()) {
            val entities = validReadings.map { it.toEntity(word.id) }
            wordReadingDao.insertWordReadings(entities)
        }
    }

    override suspend fun addReadingsForWord(wordId: Int, readings: List<KanjiReadingAPI>) {
        val validReadings = readings.filter { it.kanji.isNotBlank() || it.reading.isNotBlank() }
        if (validReadings.isNotEmpty()) {
            val entities = validReadings.map { it.toEntity(wordId) }
            wordReadingDao.insertWordReadings(entities)
        }
    }

    override fun getReadingsForWord(wordId: Int): Flow<List<KanjiReadingAPI>> {
        return wordReadingDao.getReadingsForWord(wordId).map { list ->
            list.map { it.toDomain() }
        }
    }
}
