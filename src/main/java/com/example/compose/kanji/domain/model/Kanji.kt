package com.example.compose.kanji.domain.model

import java.time.LocalTime

data class Kanji(
    val id: Int = 0,
    val character: String,          // 学
    val meaningEs: String,           // "estudio, aprender"
    val onYomi: String,        // ["ガク"]
    val kunYomi: String,       // ["まな.ぶ"]
    val jlptLevel: JlptLevel?,       // N5..N1 o null
    val strokeCount: Int,
    val exampleWords: List<ExampleWord> = emptyList(),
    val radical: String,
    val mnemonic: String,
    val notes: String,
    val dateAdded: LocalTime,
  //  val srsState: SrsState
)



/*
data class SrsState(
    val repetitions: Int = 0,
    val easeFactor: Double = 2.5,
    val intervalDays: Int = 0,
    val nextReviewDate: LocalDate,
    val lastReviewedAt: Instant? = null
)
*/
