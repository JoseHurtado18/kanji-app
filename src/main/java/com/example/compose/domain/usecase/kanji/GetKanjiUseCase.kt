package com.example.compose.domain.usecase.kanji

import com.example.compose.domain.model.Kanji
import com.example.compose.domain.repository.KanjiRepository
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