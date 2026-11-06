package com.example.compose.domain.usecase.word

import com.example.compose.domain.model.Word
import com.example.compose.domain.repository.WordRepository

class AddWordUseCase (
    private val wordRepository: WordRepository
){
    suspend operator fun invoke(
        word : Word
    ): Int{
        return wordRepository.addWord(word)
    }
}