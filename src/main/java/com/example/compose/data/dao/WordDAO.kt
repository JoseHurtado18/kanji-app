package com.example.compose.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.compose.data.entity.KanjiEntity
import com.example.compose.data.entity.WordEntity

import kotlinx.coroutines.flow.Flow

@Dao
interface WordDAO {

    @Query("""
    SELECT k.* FROM kanji_table k
    INNER JOIN kanji_word_cross_ref xref ON k.id = xref.kanjiId
    WHERE xref.wordId = :wordId
""")
    fun getKanjisForWord(wordId: Int): Flow<List<KanjiEntity>>

    @Query("SELECT * FROM word_table ORDER BY kanji_word")
    fun observeAllWords(): Flow<List<WordEntity>>

    @Query("SELECT * FROM word_table WHERE id = :wordId")
    suspend fun getWordById(wordId: Int): WordEntity?

    @Query("SELECT * FROM word_table WHERE id = :wordId")
    fun observeWordById(wordId: Int): Flow<WordEntity?>

    @Insert
    suspend fun insertWord(word: WordEntity): Long

    @Update
    suspend fun updateWord(word: WordEntity)

    @Delete
    suspend fun deleteWord(word: WordEntity)
}
