package com.example.compose.kanji

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalGridApi
import androidx.compose.foundation.layout.Grid
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
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
import com.example.compose.library.KanjiItem
import com.example.compose.ui.theme.ComposeTheme
import com.example.compose.ui.theme.NotoSans
import com.example.compose.ui.theme.Roboto


@Composable
fun MainKanjiDetail(
    modifier: Modifier = Modifier,
    kanji: String,
    mean: String,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            HeaderKanji(onBack = onBack)
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
                .verticalScroll(scrollState)

        ) {
            CardKanji(kanji, 3)
            CardMeans(mean)
            GridKvisKanjiView()
            CardEscritura()
            CardEjemplos(words = listOf(
                WordItem("学校", "escuela"),
                WordItem("学", "estudio"),
                WordItem("木", "árbol")
            ))

        }
    }

}

@Preview()
@Composable
fun PreviewMainKanji() {
    ComposeTheme {
        MainKanjiDetail(modifier = Modifier, "水", "agua", onBack = {})
    }
}

@Composable
fun HeaderKanji(onBack: () -> Unit = {}, modifier: Modifier = Modifier) {
    // Cambiamos Row por Box
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
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
            text = "Nivel 5",
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
fun CardKanji(kanji: String, score: Int, modifier: Modifier = Modifier) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp)
    ) {
        Text(
            text = kanji,
            fontFamily = NotoSans,
            color = Color.White,
            fontSize = 128.sp,
            fontWeight = FontWeight.SemiBold

        )

        Row {
            for (i in 1..score) {
                Score()
            }
        }

        Text(
            text = "En proceso de dominio",
            color = colorResource(R.color.grisclaro)
        )

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
                color = colorResource(R.color.gris),
                shape = RoundedCornerShape(8.dp)
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

@Composable
fun Score() {
    Icon(
        Icons.Default.Star,
        contentDescription = "star score",
        tint = colorResource(R.color.grisclaro)
    )
}

@Composable
fun KviKanjiView(title: String, subTitle: String, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier
            .height(94.dp)
            .width(162.dp)
            .background(
                color = colorResource(R.color.gris),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(12.dp)
    ) {
        Text(
            text = title,
            color = colorResource(R.color.grisclaro),
            fontSize = 16.sp
        )
        Text(
            text = subTitle,
            color = Color.White,
            fontSize = 24.sp
        )
    }
}

data class KanjiItem(val kanji: String, val mean: String)

@OptIn(ExperimentalGridApi::class)
@Composable
fun GridKvisKanjiView(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Grid(
            config = {
                repeat(2) { column(162.dp) }
                repeat(2) { row(size = 94.dp) }
                gap(10.dp) // espacio entre celdas, en vez de sumarlo manualmente
            }
        ) {
            Box(
                modifier = Modifier
                    .gridItem(row = 1, column = 1)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                KviKanjiView("On'yomi", "学ぶ")
            }
            Box(
                modifier = Modifier
                    .gridItem(row = 1, column = 2)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                KviKanjiView("Kun'yomi", "学ぶ")
            }
            Box(
                modifier = Modifier
                    .gridItem(row = 2, column = 1)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                KviKanjiView("Radical", "学ぶ(niño)")
            }
            Box(
                modifier = Modifier
                    .gridItem(row = 2, column = 2)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                KviKanjiView("Trazo", "8")
            }
        }
    }
}

@Composable
fun CardEscritura(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .size(width = 360.dp, height = 360.dp)
            .padding(10.dp)
            .background(
                color = colorResource(R.color.gris),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(15.dp)

    ) {
        Row(
            modifier = Modifier.fillMaxWidth(), // <-- ¡Esta es la solución!
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Orden de escritura",
                color = colorResource(R.color.grisclaro),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )

            ReproducirBtn(onClick = {})
        }
    }
}

@Composable
fun ReproducirBtn(onClick: () -> Unit, modifier: Modifier = Modifier) {
    OutlinedButton(
        onClick = onClick, // Asegúrate de pasar la variable onClick aquí
        modifier = modifier
            .width(135.dp)
            .height(35.dp),
        shape = RoundedCornerShape(10.dp),
        // Agregamos el borde grisáceo que se ve en la imagen
        border = BorderStroke(1.dp, Color.DarkGray),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Black,
            contentColor = Color.White
        ),
        contentPadding = PaddingValues(start = 8.dp, end = 12.dp)
    ) {
        // Los elementos se centrarán automáticamente
        Image(
            painter = painterResource(R.drawable.ic_play),
            contentDescription = "play boton"
        )

        // Este Spacer crea el pequeño espacio entre el icono y el texto
        //Spacer(modifier = Modifier.width(6.dp))

        Text(
            text = "Reproducir",
            fontSize = 14.sp // Reducimos un poco el texto para que encaje perfecto

        )
    }
}
data class WordItem(val word: String, val mean: String)
@Composable
fun CardEjemplos(modifier: Modifier = Modifier, words: List<WordItem>){
    Column(
        modifier = Modifier
            .width(360.dp).heightIn(100.dp, 360.dp)
            .padding(10.dp)
            .background(
                color = colorResource(R.color.gris),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(15.dp)

    ){
        Text(
            text = "Ejemplos",
            color = colorResource(R.color.grisclaro),
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold

        )

        words.forEach { item ->
            Row(
                modifier = modifier.fillMaxWidth()
                    .padding(top = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween

            ) {
                Text(
                    text = item.word,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontFamily = NotoSans
                )

                Text(
                    text = item.mean,
                    color = colorResource(R.color.grisclaro),
                    fontSize = 16.sp,
                    fontFamily = NotoSans
                )
            }
        }


    }
}

@Preview
@Composable
fun PrevCardExamples(){
    ComposeTheme() {
        CardEjemplos(modifier = Modifier, words = listOf(
            WordItem("学校", "escuela"),
            WordItem("学", "estudio"),
            WordItem("木", "árbol")
        ))
    }
}