package com.example.compose.presentation.library.kanjiLibrary

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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.compose.R
import com.example.compose.domain.model.JlptLevel
import com.example.compose.domain.model.Kanji

import com.example.compose.presentation.library.components.AddBtn
import com.example.compose.presentation.library.components.LibrarySearchBar
import com.example.compose.ui.theme.ComposeTheme
import com.example.compose.ui.theme.NotoSans
import com.example.compose.ui.theme.Roboto

@Composable
fun MainKanjiLibrary(
    onKanjiClick: (Kanji) -> Unit = {},
    onAddKanjiClick: () -> Unit = {},
    viewModel: KanjiLibraryViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        viewModel.uiMessage.collect { message ->
            when(message){
                is KanjiLibraryUiMessage.Info -> { }
            }
        }
    }

    Content(
        onKanjiClick = onKanjiClick,
        onAddKanjiClick = onAddKanjiClick,
        viewModel = viewModel,
        modifier = modifier
    )
}

@Composable
fun Content(
    onKanjiClick: (Kanji) -> Unit,
    onAddKanjiClick: () -> Unit,
    viewModel: KanjiLibraryViewModel,
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
                placeholderText = "Buscar kanji, lectura o significado"
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Chips de filtro
            FiltersChips(
                selectedFilters = state.selectedFilters,
                onFilterToggle = { filter ->
                    val newFilters = if (filter == FilterType.TODOS) {
                        setOf(FilterType.TODOS)
                    } else {
                        val withoutTodos = state.selectedFilters - FilterType.TODOS
                        if (filter in withoutTodos) {
                            val remaining = withoutTodos - filter
                            if (remaining.isEmpty()) setOf(FilterType.TODOS) else remaining
                        } else {
                            withoutTodos + filter
                        }
                    }
                    viewModel.onFilterChanged(newFilters)
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Grid de Kanjis
            LazyGridKanjis(
                lista = state.filteredKanjis,
                onKanjiClick = onKanjiClick,
                modifier = Modifier.weight(1f)
            )
        }

        AddBtn(
            onClick = onAddKanjiClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 16.dp)
        )
    }
}

enum class FilterType(val label: String) {
    TODOS("Todos"),
    N5("N5"),
    N4("N4"),
    N3("N3"),
    PENDIENTES("Pendientes");

    fun toJlptLevel(): JlptLevel? = when (this) {
        N5 -> JlptLevel.N5
        N4 -> JlptLevel.N4
        N3 -> JlptLevel.N3
        TODOS, PENDIENTES -> null
    }
}

@Composable
fun FiltersChips(
    selectedFilters: Set<FilterType>,
    onFilterToggle: (FilterType) -> Unit,
    modifier: Modifier = Modifier
) {
    val isTodosActive = selectedFilters.isEmpty() || FilterType.TODOS in selectedFilters

    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(end = 10.dp)
    ) {
        items(FilterType.entries) { filter ->
            val isSelected = if (filter == FilterType.TODOS) isTodosActive else (filter in selectedFilters)

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

@Composable
fun KviKanji(
    kanji: String,
    mean: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.kvinegro)),
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize().padding(horizontal = 4.dp)
        ) {
            Text(
                text = kanji,
                fontFamily = NotoSans,
                fontSize = 32.sp,
                color = Color.White
            )
            Text(
                text = mean,
                fontFamily = Roboto,
                fontSize = 15.sp,
                color = colorResource(R.color.grisclaro),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

data class KanjiItem(val kanji: String, val mean: String)

@Composable
fun LazyGridKanjis(
    lista: List<Kanji> = emptyList(),
    onKanjiClick: (Kanji) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(top = 4.dp, bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        columns = GridCells.Fixed(3)
    ) {
        items(lista, key = { it.id }) { item ->
            KviKanji(
                kanji = item.character,
                mean = item.meaningEs,
                onClick = { onKanjiClick(item) }
            )
        }
    }
}

@Preview
@Composable
fun PreviewLibrary() {
    ComposeTheme {
        // MainKanjiLibrary()
    }
}