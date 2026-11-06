package com.example.compose.domain.repository

import com.example.compose.domain.model.WordLearningState
import kotlinx.coroutines.flow.Flow

interface WordReviewRepository {

    fun observeDueWords(
        now: Long
    ): Flow<List<WordLearningState>>

    suspend fun getState(
        wordId: Int
    ): WordLearningState?

    suspend fun saveState(
        state: WordLearningState
    )
}