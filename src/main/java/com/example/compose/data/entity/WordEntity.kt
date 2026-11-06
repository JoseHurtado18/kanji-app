package com.example.compose.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "word_table",
    indices = [
        Index(value = ["kanji_word"])
    ]
)
data class WordEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "kanji_word")
    val kanjiWord: String,
    @ColumnInfo(name = "reading")
    val reading: String,
    @ColumnInfo(name = "meaning")
    val meaning: String,
    @ColumnInfo(name = "type_word")
    val typeWord: String?,
    @ColumnInfo(name = "sentence")
    val sentence: String? = null,
    @ColumnInfo(name = "notes")
    val notes: String? = null
)