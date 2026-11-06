package com.example.compose.domain.usecase.word

import com.example.compose.domain.model.Kanji
import com.example.compose.domain.repository.WordRepository
import kotlinx.coroutines.flow.Flow

class GetAllKanjisForWordUseCase(
    private val wordRepository: WordRepository
) {
    operator fun invoke(
        id: Int
    ) : Flow<List<Kanji>>{
        return wordRepository.getAllKanjisForWord(id)
    }
}