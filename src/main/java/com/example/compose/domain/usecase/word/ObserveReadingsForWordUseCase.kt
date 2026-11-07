package com.example.compose.domain.usecase.word

import com.example.compose.domain.model.KanjiReadingAPI
import com.example.compose.domain.repository.WordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveReadingsForWordUseCase @Inject constructor(
    private val wordRepository: WordRepository
) {
    operator fun invoke(wordId: Int): Flow<List<KanjiReadingAPI>>{
        return wordRepository.getReadingsForWord(wordId)
    }
}