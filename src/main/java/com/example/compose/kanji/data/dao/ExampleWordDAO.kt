package com.example.compose.kanji.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.compose.kanji.data.entity.ExampleWordEntity

@Dao
interface ExampleWordDAO {
    @Insert
    suspend fun insertExampleWord(
        exampleWord: ExampleWordEntity
    ): Long


    @Delete
    suspend fun deleteExampleWord(
        exampleWord: ExampleWordEntity
    )
}