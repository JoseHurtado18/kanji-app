package com.example.compose.kanji.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import androidx.room.Upsert
import com.example.compose.kanji.data.entity.KanjiEntity
import com.example.compose.kanji.data.entity.KanjiWithExampleWords
import kotlinx.coroutines.flow.Flow

@Dao
interface KanjiDAO {

    @Insert
    suspend fun insertKanji(
        kanji: KanjiEntity
    ): Long


    @Transaction
    @Query("""
        SELECT *
        FROM kanji_table
        WHERE id = :id
    """)
    fun getKanjiWithExampleWords(
        id: Int
    ): Flow<KanjiWithExampleWords?>


    @Transaction
    @Query("""
        SELECT *
        FROM kanji_table
        ORDER BY date_added DESC
    """)
    fun getAllKanjisWithExampleWords():
            Flow<List<KanjiWithExampleWords>>


    @Update
    suspend fun updateKanji(
        kanji: KanjiEntity
    )


    @Delete
    suspend fun deleteKanji(
        kanji: KanjiEntity
    )
}