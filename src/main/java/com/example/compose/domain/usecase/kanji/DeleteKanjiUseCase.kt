package com.example.compose.domain.usecase.kanji

import com.example.compose.domain.model.Kanji
import com.example.compose.domain.repository.KanjiRepository



class DeleteKanjiUseCase(
    private val repository: KanjiRepository
) {

    suspend operator fun invoke(
        kanji: Kanji
    ) {

        repository.deleteKanji(kanji)
    }
}