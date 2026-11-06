package com.example.compose.presentation.word.detalle

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.example.compose.presentation.library.components.KanjisWordCard
import com.example.compose.ui.theme.ComposeTheme
import com.example.compose.presentation.word.components.CardBtnsWord
import com.example.compose.presentation.word.components.CardWord
import com.example.compose.presentation.word.components.MeanWordCard
import com.example.compose.presentation.word.components.TopBarWord
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.compose.domain.model.TypeWord
import com.example.compose.domain.model.Word
import com.example.compose.presentation.kanji.components.CardNotas

val mockWord= Word(1, "学校", "がっこう", "escuela", TypeWord.SUSTANTIVO)
@Composable
fun WordScreen(
    word: Word,
    onBack: () -> Unit,
    onEditClic: (Word) -> Unit,
    viewModel: WordViewModel = hiltViewModel(),
    modifier: Modifier = Modifier) {

    LaunchedEffect(Unit) {
        viewModel.uiMessage.collect { message ->
            when(message){
                is WordDetailUiMessage.Info -> { }
            }
        }
    }
    val state by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = { TopBarWord(onBack = onBack) },
        bottomBar = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){
                CardBtnsWord(word, onEditClic = onEditClic)
            }
        }
    ) { contentPadding ->
        val scrollState = rememberScrollState()
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = Color.Black)
                .padding(contentPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(scrollState)
        ) {
            CardWord(word.kanjiWord, word.reading, 3)
            MeanWordCard(word)
            KanjisWordCard(modifier = modifier)
            CardNotas(word.notes ?: " ")
        }
    }
}


@Preview
@Composable
fun PreviewWordScreen() {
    ComposeTheme() {
        WordScreen(mockWord, onEditClic = {}, onBack = {}, viewModel = hiltViewModel())
    }
}