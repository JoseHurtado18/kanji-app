package com.example.compose.kanji.data.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.example.compose.kanji.data.mapper.toDomain
import com.example.compose.kanji.domain.model.JlptLevel
import com.example.compose.kanji.domain.model.Kanji

data class KanjiWithExampleWords(

    @Embedded
    val kanji: KanjiEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "kanjiId"
    )
    val exampleWords: List<ExampleWordEntity>
)


fun KanjiWithExampleWords.toDomain(): Kanji {
    return Kanji(
        id = kanji.id,
        character = kanji.character,
        meaningEs = kanji.meaningEs,
        onYomi = kanji.onYomi,
        kunYomi = kanji.kunYomi,
        jlptLevel = kanji.jlptLevel?.let {
            JlptLevel.valueOf(it)
        },
        strokeCount = kanji.strokeCount,

        exampleWords = exampleWords.map {
            it.toDomain()
        },
        radical = kanji.radical,
        mnemonic = kanji.mnemonic,
        notes = kanji.notes,

        dateAdded = kanji.dateAdded
    )
}