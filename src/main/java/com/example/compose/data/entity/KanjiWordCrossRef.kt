package com.example.compose.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "kanji_word_cross_ref",
    primaryKeys = ["kanjiId", "wordId"],
    foreignKeys = [
        ForeignKey(
            entity = KanjiEntity::class,
            parentColumns = ["id"],
            childColumns = ["kanjiId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = WordEntity::class,
            parentColumns = ["id"],
            childColumns = ["wordId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["wordId"])
    ]
)
data class KanjiWordCrossRef(
    val kanjiId: Int,
    val wordId: Int
)