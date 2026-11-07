package com.example.compose.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.compose.data.entity.WordReadingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WordReadingDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWordReading(wordReading: WordReadingEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWordReadings(wordReadings: List<WordReadingEntity>)

    @Query("SELECT * FROM word_reading_table WHERE wordId = :wordId")
    fun getReadingsForWord(wordId: Int): Flow<List<WordReadingEntity>>

    @Query("SELECT * FROM word_reading_table WHERE wordId = :wordId")
    suspend fun getReadingsForWordSync(wordId: Int): List<WordReadingEntity>

    @Query("DELETE FROM word_reading_table WHERE wordId = :wordId")
    suspend fun deleteReadingsForWord(wordId: Int)

    @Delete
    suspend fun deleteWordReading(wordReading: WordReadingEntity)
}
