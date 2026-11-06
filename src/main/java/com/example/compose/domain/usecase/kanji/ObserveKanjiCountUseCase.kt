package com.example.compose.domain.usecase.kanji

import com.example.compose.domain.repository.KanjiRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveKanjiCountUseCase @Inject constructor(
    private val repository: KanjiRepository
) {
    operator fun invoke(): Flow<Int> {
        return repository.observeKanjiCount()
    }
}