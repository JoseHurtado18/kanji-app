package com.example.compose.domain.usecase.word

import com.example.compose.domain.model.Word
import com.example.compose.domain.repository.WordRepository
import kotlinx.coroutines.flow.Flow

class GetWordUseCase(
    private val wordRepository: WordRepository
) {
    suspend operator fun invoke(
        id: Int
    ): Word? {
        return wordRepository.getWordById(id)
    }

    fun observe(id: Int): Flow<Word?> {
        return wordRepository.observeWordById(id)
    }
}
