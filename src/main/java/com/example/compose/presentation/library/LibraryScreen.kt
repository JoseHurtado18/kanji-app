package com.example.compose.presentation.library

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.compose.domain.model.Kanji
import com.example.compose.domain.model.Word
import com.example.compose.presentation.home.components.HeaderGlobal

import com.example.compose.presentation.library.kanjiLibrary.KanjiLibraryViewModel
import com.example.compose.presentation.library.kanjiLibrary.MainKanjiLibrary
import com.example.compose.presentation.library.wordLibrary.WordLibraryScreen
import com.example.compose.presentation.library.wordLibrary.WordLibraryViewModel

// Pantalla "host": sabe qué pestaña está activa y coordina el header dinámico
@Composable
fun LibraryScreen(
    onKanjiClick: (Kanji) -> Unit,
    onAddKanjiClick: () -> Unit = {},
    onWordClick: (Word) -> Unit,
    onAddWordClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    kanjiViewModel: KanjiLibraryViewModel = hiltViewModel(),
    wordViewModel: WordLibraryViewModel = hiltViewModel()
) {
    var selectedTab by rememberSaveable { mutableStateOf(LibraryTab.KANJI) }
    val kanjiState by kanjiViewModel.uiState.collectAsState()
    val wordState by wordViewModel.uiState.collectAsState()

    val subtitle = if (selectedTab == LibraryTab.KANJI) {
        "${kanjiState.kanjis.size} kanjis"
    } else {
        "${wordState.words.size} palabras"
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            HeaderGlobal(fecha = "", title = "Biblioteca", subtitle = subtitle)

            Spacer(modifier = Modifier.height(10.dp))

            LibraryTabSwitcher(
                selected = selectedTab,
                onSelect = { selectedTab = it }
            )

            when (selectedTab) {
                LibraryTab.KANJI -> MainKanjiLibrary(
                    onKanjiClick = onKanjiClick,
                    onAddKanjiClick = onAddKanjiClick,
                    viewModel = kanjiViewModel,
                    modifier = Modifier.weight(1f)
                )
                LibraryTab.WORDS -> WordLibraryScreen(
                    onWordClick = onWordClick,
                    onAddWordClick = onAddWordClick,
                    viewModel = wordViewModel,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}
