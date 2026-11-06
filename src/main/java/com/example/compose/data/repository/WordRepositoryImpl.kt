package com.example.compose.data.repository

import com.example.compose.data.dao.WordDAO
import com.example.compose.data.dao.WordLearningStateDao
import com.example.compose.data.mapper.toDomain
import com.example.compose.data.mapper.toEntity
import com.example.compose.domain.model.Kanji
import com.example.compose.domain.model.Word
import com.example.compose.domain.repository.WordRepository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WordRepositoryImpl @Inject constructor(
    private val wordDao: WordDAO,
    private val learningStateDao: WordLearningStateDao
): WordRepository{

    override fun getAllKanjisForWord(id: Int): Flow<List<Kanji>> {
        return wordDao.getKanjisForWord(id).map { list ->
            list.map {
                it.toDomain()
            }
        }
    }

    override fun observeAllWords(): Flow<List<Word>> {
        return combine(
            wordDao.observeAllWords(),
            learningStateDao.observeDueWords(System.currentTimeMillis())
        ) { wordEntities, dueStates ->
            val dueWordIds = dueStates.map { it.wordId }.toSet()
            wordEntities.map { entity ->
                entity.toDomain().copy(
                    isDueForReview = entity.id in dueWordIds
                )
            }
        }
    }

    override suspend fun getWordById(id: Int): Word? {
        return wordDao.getWordById(id)?.toDomain()
    }

    override fun observeWordById(id: Int): Flow<Word?> {
        return wordDao.observeWordById(id).map { it?.toDomain() }
    }

    override suspend fun addWord(word: Word): Int {
        return wordDao.insertWord(word.toEntity()).toInt()
    }

    override suspend fun updateWord(word: Word) {
        wordDao.updateWord(word.toEntity())
    }
}
