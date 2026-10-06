package com.example.compose.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.compose.kanji.data.dao.ExampleWordDAO
import com.example.compose.kanji.data.dao.KanjiDAO
import com.example.compose.kanji.data.entity.ExampleWordEntity
import com.example.compose.kanji.data.entity.KanjiEntity


@Database(entities = [KanjiEntity::class, ExampleWordEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getKanjiDAO(): KanjiDAO
    abstract fun getExampleWordDAO(): ExampleWordDAO
}