package com.example.compose.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "word_learning_state",
    foreignKeys = [
        ForeignKey(
            entity = WordEntity::class,
            parentColumns = ["id"],
            childColumns = ["wordId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["nextReviewAt"])
    ]
)
data class WordLearningStateEntity(
    @PrimaryKey
    val wordId: Int,

    val status: String = "NEW",

    val repetitions: Int = 0,

    val intervalDays: Int = 0,

    val easeFactor: Double = 2.5,

    val lastReviewedAt: Long? = null,

    val nextReviewAt: Long? = null
)