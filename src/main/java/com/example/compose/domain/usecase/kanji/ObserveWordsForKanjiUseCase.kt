package com.example.compose.domain.usecase.kanji

import com.example.compose.domain.model.Word
import com.example.compose.domain.repository.KanjiRepository

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveWordsForKanjiUseCase @Inject constructor(
    private val kanjiRepository: KanjiRepository
) {
    operator fun invoke(kanjiId: Int): Flow<List<Word>> {
        return kanjiRepository.observeWordsForKanji(kanjiId)
    }
}
