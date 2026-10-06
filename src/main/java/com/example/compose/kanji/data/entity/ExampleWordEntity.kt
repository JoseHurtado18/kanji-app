package com.example.compose.kanji.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "example_word_table",
    foreignKeys = [
        ForeignKey(
            entity = KanjiEntity::class,
            parentColumns = ["id"],
            childColumns = ["kanjiId"],
            onDelete = ForeignKey.CASCADE
        )
    ],

    indices = [
        Index("kanjiId")
    ]
)
data class ExampleWordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val kanjiId: Int,

    val word: String,
    val reading: String,
    val meaningEs: String
)