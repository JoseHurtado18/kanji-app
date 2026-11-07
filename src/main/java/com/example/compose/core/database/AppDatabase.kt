package com.example.compose.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.compose.data.dao.ExampleWordDAO
import com.example.compose.data.dao.KanjiDAO
import com.example.compose.data.dao.WordDAO
import com.example.compose.data.dao.WordLearningStateDao
import com.example.compose.data.dao.WordReadingDAO
import com.example.compose.data.entity.ExampleWordEntity
import com.example.compose.data.entity.KanjiEntity
import com.example.compose.data.entity.KanjiWordCrossRef
import com.example.compose.data.entity.WordEntity
import com.example.compose.data.entity.WordLearningStateEntity
import com.example.compose.data.entity.WordReadingEntity

@Database(
    entities = [
        KanjiEntity::class,
        ExampleWordEntity::class,
        WordEntity::class,
        KanjiWordCrossRef::class,
        WordLearningStateEntity::class,
        WordReadingEntity::class
    ], 
    version = 4
)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getKanjiDAO(): KanjiDAO
    abstract fun getExampleWordDAO(): ExampleWordDAO
    abstract fun getWordDAO(): WordDAO
    abstract fun getWordLearningStateDao(): WordLearningStateDao
    abstract fun getWordReadingDAO(): WordReadingDAO
}
