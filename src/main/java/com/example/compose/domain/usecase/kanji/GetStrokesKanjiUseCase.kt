package com.example.compose.domain.usecase.kanji

import com.example.compose.domain.repository.KanjiStrokeRepository

class GetStrokesKanjiUseCase (
    private val repository: KanjiStrokeRepository
){
    suspend operator fun invoke(
        caracter : String
    ):List<String>{
        return repository.getStrokesForKanji(caracter)
    }
}