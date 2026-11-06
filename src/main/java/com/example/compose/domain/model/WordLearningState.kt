package com.example.compose.domain.model


enum class WordLearningStatus {
    NEW,
    LEARNING,
    REVIEW,
    MASTERED
}

data class WordLearningState(
    val wordId: Int,
    val status: WordLearningStatus,
    val repetitions: Int,
    val intervalDays: Int,
    val easeFactor: Double,
    val lastReviewedAt: Long?,
    val nextReviewAt: Long?
)