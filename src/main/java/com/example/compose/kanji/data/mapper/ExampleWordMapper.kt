package com.example.compose.kanji.data.mapper

import com.example.compose.kanji.data.entity.ExampleWordEntity
import com.example.compose.kanji.domain.model.ExampleWord

fun ExampleWordEntity.toDomain(): ExampleWord {
    return ExampleWord(
        id = id,
        word = word,
        reading = reading,
        meaningEs = meaningEs
    )
}

fun ExampleWord.toEntity(
    kanjiId: Int
): ExampleWordEntity {
    return ExampleWordEntity(
        id = id,
        kanjiId = kanjiId,
        word = word,
        reading = reading,
        meaningEs = meaningEs
    )
}