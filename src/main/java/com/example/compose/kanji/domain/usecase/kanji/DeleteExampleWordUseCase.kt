package com.example.compose.kanji.domain.usecase.kanji

import com.example.compose.kanji.domain.model.ExampleWord
import com.example.compose.kanji.domain.repository.KanjiRepository

class DeleteExampleWordUseCase(
    private val repository: KanjiRepository
) {

    suspend operator fun invoke(
        kanjiId: Int,
        exampleWord: ExampleWord
    ) {

        repository.deleteExampleWord(
            kanjiId = kanjiId,
            exampleWord = exampleWord
        )
    }
}