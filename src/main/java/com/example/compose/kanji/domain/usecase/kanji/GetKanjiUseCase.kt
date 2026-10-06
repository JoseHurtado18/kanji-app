package com.example.compose.kanji.domain.usecase.kanji

import com.example.compose.kanji.domain.model.Kanji
import com.example.compose.kanji.domain.repository.KanjiRepository
import kotlinx.coroutines.flow.Flow

class GetKanjiUseCase(
    private val repository: KanjiRepository
) {

    operator fun invoke(
        id: Int
    ): Flow<Kanji?> {

        return repository.getKanjiById(id)
    }
}