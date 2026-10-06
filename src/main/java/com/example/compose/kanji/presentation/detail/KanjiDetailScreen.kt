package com.example.compose.kanji.presentation.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.R
import com.example.compose.kanji.domain.model.Kanji
import com.example.compose.kanji.presentation.components.CardBtns
import com.example.compose.kanji.presentation.components.CardEjemplos
import com.example.compose.kanji.presentation.components.CardEscritura
import com.example.compose.kanji.presentation.components.CardKanji
import com.example.compose.kanji.presentation.components.CardMnemotecnia
import com.example.compose.kanji.presentation.components.CardNotas
import com.example.compose.kanji.presentation.components.GridKvisKanjiView
import com.example.compose.kanji.presentation.components.WordItem
import com.example.compose.roundedCornerShapeValue
import com.example.compose.ui.theme.ComposeTheme
import com.example.compose.ui.theme.Roboto


@Composable
fun MainKanjiDetail(
    onEditClic : (Kanji) -> Unit = {},
    modifier: Modifier = Modifier,
    kanji: Kanji,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            HeaderKanji( kanji.jlptLevel.toString(),onBack = onBack)
        },
        bottomBar = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){
                CardBtns(kanji = kanji, onEditClic = onEditClic,modifier = Modifier.width(360.dp))
            }
             }

    ) { contentpadding ->
        val scrollState = rememberScrollState()

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = Color.Black)
                .padding(contentpadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(scrollState)

        ) {
            CardKanji(kanji.character, 3)
            CardMeans(kanji.meaningEs)
            GridKvisKanjiView(kanji.onYomi, kanji.kunYomi, kanji.radical,
                kanji.strokeCount)
            CardEscritura()
            CardEjemplos(words = listOf(
                WordItem("学校", "escuela"),
                WordItem("学", "estudio"),
                WordItem("木", "árbol")
            ))
            CardMnemotecnia(kanji.mnemonic)
            CardNotas(kanji.notes)

        }
    }

}


@Preview()
@Composable
fun PreviewMainKanji() {
    ComposeTheme {
        //MainKanjiDetail(onEditClic = {},modifier = Modifier, kanji =, onBack = {})
    }
}

@Composable
fun HeaderKanji(nivel: String,onBack: () -> Unit = {}, modifier: Modifier = Modifier) {
    // Cambiamos Row por Box
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.Transparent)
    ) {
        // 1. Botón a la izquierda
        IconButton(
            onClick = onBack,
            modifier = Modifier.align(Alignment.CenterStart) // Alineado al inicio
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_arrow_back),
                contentDescription = "flecha volver",
                tint = Color.White
            )
        }

        // 2. Texto en el centro exacto
        Text(
            text = nivel,
            color = Color.White,
            fontSize = 24.sp,
            fontFamily = Roboto,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(Alignment.Center) // Alineado al centro absoluto del Box
        )

        // 3. Menú a la derecha
        // Lo envolvemos en un Box por si tu MinimalDropdownMenu no acepta el parámetro modifier
        Box(
            modifier = Modifier.align(Alignment.CenterEnd) // Alineado al final
        ) {
            MinimalDropdownMenu()
        }
    }
}

@Composable
fun MinimalDropdownMenu() {
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .padding(16.dp)
    ) {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(Icons.Default.MoreVert, contentDescription = "More options", tint = Color.White)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Option 1") },
                onClick = { /* Do something... */ }
            )
            DropdownMenuItem(
                text = { Text("Option 2") },
                onClick = { /* Do something... */ }
            )
        }
    }
}



@Composable
fun CardMeans(mean: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .size(width = 360.dp, height = 100.dp)
            .fillMaxWidth()
            .padding(10.dp)
            .background(
                color = colorResource(R.color.grisdestacado),
                shape = RoundedCornerShape(roundedCornerShapeValue)
            )
            .padding(15.dp)

    ) {
        Text(
            text = mean,
            fontSize = 24.sp,
            color = Color.White
        )
        Text(
            text = "Significado principal",
            fontSize = 16.sp,
            color = colorResource(R.color.grisclaro)
        )
    }
}


