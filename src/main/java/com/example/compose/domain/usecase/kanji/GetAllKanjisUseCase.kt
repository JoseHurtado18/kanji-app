package com.example.compose.domain.usecase.kanji

import com.example.compose.domain.model.Kanji
import com.example.compose.domain.repository.KanjiRepository

import kotlinx.coroutines.flow.Flow

class GetAllKanjisUseCase(
    private val repository: KanjiRepository
) {

    operator fun invoke():
            Flow<List<Kanji>> {

        return repository.getAllKanjis()
    }
}