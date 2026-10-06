package com.example.compose.library.presentation

import com.example.compose.kanji.domain.model.JlptLevel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalGridApi
import androidx.compose.foundation.layout.Grid
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.SelectableChipColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compose.R
import com.example.compose.home.components.HeaderGlobal
import com.example.compose.kanji.domain.model.Kanji
import com.example.compose.kanji.kanjisList
import com.example.compose.library.presentation.components.AddBtn
import com.example.compose.library.presentation.components.SimpleSearchBarExample
import com.example.compose.ui.theme.ComposeTheme
import com.example.compose.ui.theme.NotoSans
import com.example.compose.ui.theme.Roboto
import dagger.hilt.android.lifecycle.HiltViewModel

class LibraryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeTheme() {
                //MainLibrary()
            }
        }
    }
}


@Composable
fun MainLibrary(
    onKanjiClick: (Kanji) -> Unit = {},
    onAddKanjiClick:() -> Unit ={},
    viewModel: LibraryViewModel,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        viewModel.uiMessage.collect { message ->
            when(message){
                is LibraryUiMessage.Info -> {  }
            }
        }
    }

    Content(onKanjiClick = onKanjiClick,
        onAddKanjiClick = onAddKanjiClick,
        viewModel = viewModel ,
        modifier = modifier)
}

@Composable
fun Content(
    onKanjiClick: (Kanji) -> Unit,
    onAddKanjiClick: () -> Unit,
    viewModel: LibraryViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.uiState.collectAsState()
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
            HeaderGlobal( "", "Biblioteca", state.total_kanjis.toString() +" "+"kanjis")
            // Dejamos un espacio de la altura aproximada que ocupará el SearchBar
            Spacer(modifier = Modifier
                .height(72.dp)
                .background(Color.White))

            FiltersChips(
                selectedFilters = state.selectedFilters,
                onFilterToggle = { filter ->
                    val newFilters = if (filter in state.selectedFilters) {
                        state.selectedFilters - filter
                    } else {
                        state.selectedFilters + filter
                    }
                    viewModel.onFilterChanged(newFilters)
                }
            )
            LazyGridKanjis(state.filteredKanjis, onKanjiClick = onKanjiClick)

        }


        AddBtn(
            onClick = onAddKanjiClick,
            modifier = Modifier
                .align(Alignment.BottomEnd) // Ahora el BoxScope permite esto
                .padding(end = 24.dp) // Ajusta el margen a tu gusto
        )

        // SearchBar superpuesto.
        // Aplicamos un padding superior e inferior de 16.dp para que coincida con los márgenes del Header
        SimpleSearchBarExample(
            modifier = Modifier
                .fillMaxSize()
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
    LazyRow(
        modifier = modifier.padding(top = 30.dp), // Se usa el modifier recibido
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        contentPadding = PaddingValues(
            start = 0.dp,    // Pegado completamente al borde izquierdo
            end = 10.dp      // Mantiene espacio al final del scroll
        )
    ) {
        items(FilterType.entries) { filter ->
            val isSelected = filter in selectedFilters

            FilterChip(
                colors = SelectableChipColors(
                    containerColor = Color.Black,
                    labelColor = Color.White,
                    leadingIconColor = Color.White,
                    trailingIconColor = Color.White,
                    disabledContainerColor = Color.Gray,
                    disabledLabelColor = Color.Gray,
                    disabledLeadingIconColor = Color.Gray,
                    disabledTrailingIconColor = Color.Gray,
                    selectedContainerColor = Color.White,
                    disabledSelectedContainerColor = Color.Gray,
                    selectedLabelColor = Color.Black,
                    selectedLeadingIconColor = Color.Black,
                    selectedTrailingIconColor = Color.Black

                ),
                selected = isSelected,
                onClick = { onFilterToggle(filter) },
                label = { Text(filter.label, fontSize = 16.sp) },
                leadingIcon = if (isSelected) {
                    {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = null,
                            modifier = Modifier.size(FilterChipDefaults.IconSize)
                        )
                    }
                } else null
            )
        }
    }
}

@Preview(showBackground = true,
    showSystemUi = true,
    device = "spec:width=411dp,height=891dp,dpi=420")
@Composable
fun PreviewLibrary() {
    ComposeTheme {
        //MainLibrary(viewModel = @HiltViewModel)
    }
}

@Preview
@Composable
fun PreviewChips() {
    ComposeTheme() {
        FiltersChips(
            selectedFilters = emptySet(),
            onFilterToggle = {}
        )
    }
}

@Composable
fun KviKanji(kanji: String, mean: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Card(
        onClick = onClick, // La Card acepta onClick directamente
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.kvinegro)),
        modifier = modifier.size(100.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
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
                fontSize = 16.sp,
                color = Color.White
            )
        }
    }

}

data class KanjiItem(val kanji: String, val mean: String)

@Composable
fun LazyGridKanjis(lista: List<Kanji> = emptyList(), onKanjiClick: (Kanji) -> Unit, modifier: Modifier = Modifier){
    LazyVerticalGrid(
        modifier = Modifier.padding(top = 30.dp),
        contentPadding = PaddingValues(bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        columns = GridCells.Fixed(3)) {
        items(lista){item ->
            KviKanji(
                kanji = item.character,
                mean = item.meaningEs,
                onClick = { onKanjiClick(item) }
            )
        }
    }
}