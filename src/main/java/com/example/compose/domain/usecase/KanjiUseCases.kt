package com.example.compose.domain.usecase

import com.example.compose.domain.usecase.kanji.AddExampleWordUseCase
import com.example.compose.domain.usecase.kanji.AddKanjiUseCase
import com.example.compose.domain.usecase.kanji.DeleteExampleWordUseCase
import com.example.compose.domain.usecase.kanji.DeleteKanjiUseCase
import com.example.compose.domain.usecase.kanji.GetAllKanjisUseCase
import com.example.compose.domain.usecase.kanji.GetKanjiUseCase
import com.example.compose.domain.usecase.kanji.GetStrokesKanjiUseCase
import com.example.compose.domain.usecase.kanji.ObserveKanjiCountUseCase
import com.example.compose.domain.usecase.kanji.ObserveWordsForKanjiUseCase
import com.example.compose.domain.usecase.kanji.UpdateKanjiUseCase

data class KanjiUseCases(

    val addKanji: AddKanjiUseCase,

    val getKanji: GetKanjiUseCase,

    val getAllKanjis: GetAllKanjisUseCase,

    val updateKanji: UpdateKanjiUseCase,

    val deleteKanji: DeleteKanjiUseCase,

    val addExampleWord: AddExampleWordUseCase,

    val deleteExampleWord: DeleteExampleWordUseCase,

    val getStrokesKanji: GetStrokesKanjiUseCase,

    val observeKanjiCount: ObserveKanjiCountUseCase,

    val observeWordsForKanji: ObserveWordsForKanjiUseCase
)