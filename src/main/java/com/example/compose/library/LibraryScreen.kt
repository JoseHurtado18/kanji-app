package com.example.compose.library

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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.ui.theme.ComposeTheme
import com.example.compose.ui.theme.Roboto
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.SelectableChipColors
import androidx.compose.ui.res.colorResource
import com.example.compose.R
import com.example.compose.ui.theme.NotoSans

class LibraryActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeTheme() {
                MainLibrary()
            }
        }
    }
}


@Composable
fun HeaderLibrary(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(top = 30.dp)
    ) {
        Text(
            text = "Buenos dias",
            fontSize = 32.sp,
            color = Color.White,
            fontFamily = Roboto,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "156 kanjis",
            fontSize = 18.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun Content(
    onKanjiClick: (KanjiItem) -> Unit,
    modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Contenido de fondo (Header y contenido de biblioteca)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            HeaderLibrary()

            // Dejamos un espacio de la altura aproximada que ocupará el SearchBar
            Spacer(modifier = Modifier.height(72.dp).background(Color.White))

            // Aquí puedes agregar la lista o el contenido principal de tus Kanjis

            FiltersChips()
            GridKviKanjis(
                kanjis = listOf(
                    KanjiItem("水", "agua"),
                    KanjiItem("学", "estudio"),
                    KanjiItem("木", "árbol")
                ),
                onKanjiClick = onKanjiClick
            )
        }

        // SearchBar superpuesto.
        // Aplicamos un padding superior e inferior de 16.dp para que coincida con los márgenes del Header
        SimpleSearchBarExample(
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

@Composable
fun MainLibrary(onKanjiClick: (KanjiItem) -> Unit = {}, modifier: Modifier = Modifier) {
    Content(onKanjiClick = onKanjiClick, modifier = modifier)
}
enum class FilterType(val label: String) {
    TODOS("Todos"),
    N5("N5"),
    N4("N4"),
    N3("N3"),
    PENDIENTES("Pendientes")
}

@Composable
fun FiltersChips(
    modifier: Modifier = Modifier,
    onSelectionChanged: (Set<FilterType>) -> Unit = {}
) {
    var selectedFilters by remember { mutableStateOf(setOf<FilterType>()) }

    LazyRow (
        modifier = modifier.padding(top = 10.dp), // Se usa el modifier recibido
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
                    labelColor=  Color.White,
                    leadingIconColor= Color.White,
                    trailingIconColor= Color.White,
                    disabledContainerColor= Color.Gray,
                    disabledLabelColor= Color.Gray,
                    disabledLeadingIconColor= Color.Gray,
                    disabledTrailingIconColor= Color.Gray,
                    selectedContainerColor= Color.White,
                    disabledSelectedContainerColor= Color.Gray,
                    selectedLabelColor= Color.Black,
                    selectedLeadingIconColor= Color.Black,
                    selectedTrailingIconColor= Color.Black

                ),
                selected = isSelected,
                onClick = {
                    selectedFilters = if (isSelected) {
                        selectedFilters - filter
                    } else {
                        selectedFilters + filter
                    }
                    onSelectionChanged(selectedFilters)
                },
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

@Preview(showBackground = true)
@Composable
fun PreviewLibrary() {
    ComposeTheme {
        MainLibrary()
    }
}

@Preview
@Composable
fun PreviewChips(){
    ComposeTheme() {
        FiltersChips()
    }
}

@Composable
fun KviKanji(kanji: String, mean: String, onClick: () -> Unit, modifier: Modifier = Modifier){
    Card(
        onClick = onClick, // La Card acepta onClick directamente
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.kvinegro)),
        modifier = modifier.size(100.dp)
    ) {
        Column(verticalArrangement = Arrangement.Center,
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
@OptIn(ExperimentalGridApi::class)
@Composable
fun GridKviKanjis(
    kanjis: List<KanjiItem>,
    onKanjiClick: (KanjiItem) -> Unit,
    modifier: Modifier = Modifier
){
    Box( modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center) {
        Grid(
            config = {
                repeat(3) { column(110.dp) }
                repeat(3) { row(size = 137.dp) }
            },
            modifier = Modifier.padding(top = 30.dp)
        ) {
            kanjis.forEach { item ->
                KviKanji(
                    kanji = item.kanji,
                    mean = item.mean,
                    onClick = { onKanjiClick(item) }
                )
        }
    }
}
}
