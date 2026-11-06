package com.example.compose.domain.usecase.word

import com.example.compose.domain.model.Word
import com.example.compose.domain.repository.WordRepository

class UpdateWordUseCase(
    private val repository: WordRepository
) {
    suspend operator fun invoke(
        word: Word
    ) {
        repository.updateWord(word)
    }
}
