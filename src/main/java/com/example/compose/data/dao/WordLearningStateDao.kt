package com.example.compose.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.compose.data.entity.WordLearningStateEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WordLearningStateDao {

    @Query("""
        SELECT * FROM word_learning_state
        WHERE nextReviewAt IS NOT NULL
          AND nextReviewAt <= :now
        ORDER BY nextReviewAt ASC
    """)
    fun observeDueWords(now: Long): Flow<List<WordLearningStateEntity>>

    @Query("""
        SELECT * FROM word_learning_state
        WHERE wordId = :wordId
    """)
    suspend fun getState(wordId: Int): WordLearningStateEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveState(state: WordLearningStateEntity)

    @Query("""
        SELECT COUNT(*) FROM word_learning_state
        WHERE status = 'MASTERED'
    """)
    fun observeMasteredWordCount(): Flow<Int>
}