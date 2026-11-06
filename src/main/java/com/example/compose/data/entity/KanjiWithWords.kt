package com.example.compose.data.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class KanjiWithWords(
    @Embedded
    val kanji: KanjiEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = KanjiWordCrossRef::class,
            parentColumn = "kanjiId",
            entityColumn = "wordId"
        )
    )
    val words: List<WordEntity>
)