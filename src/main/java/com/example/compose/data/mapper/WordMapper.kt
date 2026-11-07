package com.example.compose.data.mapper

import com.example.compose.data.entity.WordEntity
import com.example.compose.data.entity.WordReadingEntity
import com.example.compose.domain.model.KanjiReadingAPI
import com.example.compose.domain.model.TypeWord
import com.example.compose.domain.model.Word

fun WordEntity.toDomain(): Word =
    Word(
        id = id,
        kanjiWord = kanjiWord,
        reading = reading,
        meaning = meaning,
        typeWord = typeWord?.let { runCatching { TypeWord.valueOf(it) }.getOrNull() },
        sentence = sentence,
        notes = notes
    )

fun Word.toEntity(): WordEntity =
    WordEntity(
        id = id,
        kanjiWord = kanjiWord,
        reading = reading,
        meaning = meaning,
        typeWord = typeWord?.name,
        sentence = sentence,
        notes = notes
    )

fun WordReadingEntity.toDomain(): KanjiReadingAPI =
    KanjiReadingAPI(
        kanji = kanji,
        reading = reading
    )

fun KanjiReadingAPI.toEntity(wordId: Int = 0): WordReadingEntity =
    WordReadingEntity(
        wordId = wordId,
        kanji = kanji,
        reading = reading
    )
