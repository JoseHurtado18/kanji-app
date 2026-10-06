package com.example.compose.kanji.data.mapper

import com.example.compose.kanji.data.entity.KanjiEntity
import com.example.compose.kanji.domain.model.JlptLevel
import com.example.compose.kanji.domain.model.Kanji

fun KanjiEntity.toDomain(): Kanji {
    return Kanji(
        id = id,
        character = character,
        meaningEs = meaningEs,
        onYomi = onYomi,
        kunYomi = kunYomi,
        jlptLevel = jlptLevel?.let { JlptLevel.valueOf(it) },
        strokeCount = strokeCount,
        exampleWords = emptyList(),
        radical = radical,
        mnemonic = mnemonic,
        notes = notes,
        dateAdded = dateAdded
    )
}

fun Kanji.toEntity(): KanjiEntity {
    return KanjiEntity(
        id = id,
        character = character,
        meaningEs = meaningEs,
        onYomi = onYomi,
        kunYomi = kunYomi,
        jlptLevel = jlptLevel?.name,
        strokeCount = strokeCount,
        radical = radical,
        mnemonic = mnemonic,
        notes = notes,
        dateAdded = dateAdded
    )
}