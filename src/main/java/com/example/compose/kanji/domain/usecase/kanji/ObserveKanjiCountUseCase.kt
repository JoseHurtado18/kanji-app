package com.example.compose.kanji.domain.usecase.kanji

import com.example.compose.kanji.domain.repository.KanjiRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveKanjiCountUseCase @Inject constructor(
    private val repository: KanjiRepository
) {
    operator fun invoke(): Flow<Int> {
        return repository.observeKanjiCount()
    }
}