package com.example.compose.domain.usecase

import com.example.compose.domain.usecase.word.AddWordUseCase
import com.example.compose.domain.usecase.word.GetAllKanjisForWordUseCase
import com.example.compose.domain.usecase.word.GetAllWordsUseCase
import com.example.compose.domain.usecase.word.GetWordUseCase
import com.example.compose.domain.usecase.word.ObserveReadingsForWordUseCase
import com.example.compose.domain.usecase.word.UpdateWordUseCase

data class WordUseCases (
    val addWordUseCase: AddWordUseCase,
    val getAllWordsUseCase: GetAllWordsUseCase,
    val getWordUseCase: GetWordUseCase,
    val getAllKanjisForWordUseCase: GetAllKanjisForWordUseCase,
    val updateWordUseCase: UpdateWordUseCase,
    val observeReadingsForWordUseCase: ObserveReadingsForWordUseCase
)
