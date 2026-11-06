package com.example.compose.presentation.library.wordLibrary

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.compose.domain.model.TypeWord
import com.example.compose.domain.model.Word

import com.example.compose.presentation.library.components.AddBtn
import com.example.compose.presentation.library.components.LibrarySearchBar
import com.example.compose.presentation.library.components.WordList
import com.example.compose.ui.theme.ComposeTheme

@Composable
fun WordLibraryScreen(
    onWordClick: (Word) -> Unit = {},
    onAddWordClick: () -> Unit = {},
    viewModel: WordLibraryViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        viewModel.uiMessage.collect { message ->
            when(message){
                is WordLibraryUiMessage.Info -> { }
            }
        }
    }

    ContentWordLibrary(
        onWordClick = onWordClick,
        onAddWordClick = onAddWordClick,
        viewModel = viewModel,
        modifier = modifier
    )
}

@Composable
fun ContentWordLibrary(
    onWordClick: (Word) -> Unit,
    onAddWordClick: () -> Unit = {},
    viewModel: WordLibraryViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(14.dp))

            // Buscador
            LibrarySearchBar(
                query = state.searchQuery,
                onQueryChange = { viewModel.onSearchQueryChanged(it) },
                placeholderText = "Buscar palabra, lectura o significado"
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Chips de filtrado
            FiltersChipsWords(
                selectedFilters = state.selectedFilters,
                onFilterToggle = { filter ->
                    val newFilters = if (filter == FilterTypeWord.TODOS) {
                        setOf(FilterTypeWord.TODOS)
                    } else {
                        val withoutTodos = state.selectedFilters - FilterTypeWord.TODOS
                        if (filter in withoutTodos) {
                            val remaining = withoutTodos - filter
                            if (remaining.isEmpty()) setOf(FilterTypeWord.TODOS) else remaining
                        } else {
                            withoutTodos + filter
                        }
                    }
                    viewModel.onFilterChanged(newFilters)
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Lista de palabras
            WordList(
                words = state.filteredWords,
                onWordClick = onWordClick,
                modifier = Modifier.weight(1f)
            )
        }

        AddBtn(
            onClick = onAddWordClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 16.dp)
        )
    }
}

enum class FilterTypeWord(val label: String) {
    TODOS("Todas"),
    SUSTANTIVOS("Sustantivos"),
    VERBOS("Verbos"),
    ADJETIVOS("Adjetivos"),
    PENDIENTES("Pendientes");

    fun toTypeWord(): TypeWord? = when (this) {
        SUSTANTIVOS -> TypeWord.SUSTANTIVO
        VERBOS -> TypeWord.VERBO
        ADJETIVOS -> TypeWord.ADJETIVO
        TODOS, PENDIENTES -> null
    }
}

@Composable
fun FiltersChipsWords(
    selectedFilters: Set<FilterTypeWord>,
    onFilterToggle: (FilterTypeWord) -> Unit,
    modifier: Modifier = Modifier
) {
    val isTodosActive = selectedFilters.isEmpty() || FilterTypeWord.TODOS in selectedFilters

    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(end = 10.dp)
    ) {
        items(FilterTypeWord.entries) { filter ->
            val isSelected = if (filter == FilterTypeWord.TODOS) isTodosActive else (filter in selectedFilters)

            Surface(
                onClick = { onFilterToggle(filter) },
                shape = RoundedCornerShape(50),
                color = if (isSelected) Color.White else Color.Transparent,
                border = if (isSelected) null else BorderStroke(1.dp, Color(0xFF2E2E2E))
            ) {
                Text(
                    text = filter.label,
                    color = if (isSelected) Color.Black else Color.White,
                    fontSize = 14.sp,
                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewWordLibraryScreen() {
    ComposeTheme {
        // WordLibraryScreen()
    }
}