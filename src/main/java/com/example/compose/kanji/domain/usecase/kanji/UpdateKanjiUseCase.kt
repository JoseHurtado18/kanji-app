package com.example.compose.kanji.domain.usecase.kanji

import com.example.compose.kanji.domain.model.Kanji
import com.example.compose.kanji.domain.repository.KanjiRepository

class UpdateKanjiUseCase(
    private val repository: KanjiRepository
) {

    suspend operator fun invoke(
        kanji: Kanji
    ) {

        repository.updateKanji(kanji)
    }
}