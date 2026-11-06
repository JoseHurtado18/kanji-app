package com.example.compose.domain.usecase.kanji

import com.example.compose.domain.model.Kanji
import com.example.compose.domain.repository.KanjiRepository

class UpdateKanjiUseCase(
    private val repository: KanjiRepository
) {

    suspend operator fun invoke(
        kanji: Kanji
    ) {

        repository.updateKanji(kanji)
    }
}