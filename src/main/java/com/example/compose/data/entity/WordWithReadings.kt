package com.example.compose.data.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.example.compose.data.mapper.toDomain
import com.example.compose.domain.model.Word

data class WordWithReadings(
    @Embedded
    val word: WordEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "wordId"
    )
    val readings: List<WordReadingEntity>
)

fun WordWithReadings.toDomain(): Word {
    return word.toDomain().copy(
        readings = readings.map { it.toDomain() }
    )
}
