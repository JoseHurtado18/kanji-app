package com.example.compose.kanji.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalGridApi
import androidx.compose.foundation.layout.Grid
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.R
import com.example.compose.roundedCornerShapeValue

@Composable
fun KviKanjiView(title: String, subTitle: String, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier
            .height(94.dp)
            .width(162.dp)
            .background(
                color = colorResource(R.color.card_secundaria),
                shape = RoundedCornerShape(roundedCornerShapeValue)
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
fun GridKvisKanjiView(onyomi: String, kunyomi: String, radical: String, trazo: Int, modifier: Modifier = Modifier) {
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
                KviKanjiView("On'yomi", onyomi)
            }
            Box(
                modifier = Modifier
                    .gridItem(row = 1, column = 2)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                KviKanjiView("Kun'yomi", kunyomi)
            }
            Box(
                modifier = Modifier
                    .gridItem(row = 2, column = 1)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                KviKanjiView("Radical", radical)
            }
            Box(
                modifier = Modifier
                    .gridItem(row = 2, column = 2)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                KviKanjiView("Trazo", trazo.toString())
            }
        }
    }
}