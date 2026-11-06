package com.example.compose.domain.usecase.word

import com.example.compose.domain.model.Word
import com.example.compose.domain.repository.WordRepository
import kotlinx.coroutines.flow.Flow


class GetAllWordsUseCase(
    private val wordRepository: WordRepository
) {
    operator fun invoke()
        :Flow<List<Word>>{
            return wordRepository.observeAllWords()
        }

}