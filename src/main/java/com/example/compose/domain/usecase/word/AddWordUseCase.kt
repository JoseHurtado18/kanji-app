package com.example.compose.domain.usecase.word

import com.example.compose.domain.model.KanjiReadingAPI
import com.example.compose.domain.model.Word
import com.example.compose.domain.repository.WordRepository

class AddWordUseCase(
    private val wordRepository: WordRepository
) {
    suspend operator fun invoke(
        word: Word
    ): Int {
        return wordRepository.addWord(word)
    }

    suspend operator fun invoke(
        word: Word,
        readings: List<KanjiReadingAPI>
    ): Int {
        return wordRepository.addWord(word.copy(readings = readings))
    }
}
