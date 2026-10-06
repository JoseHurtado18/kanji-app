package com.example.compose.kanji.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.R
import com.example.compose.roundedCornerShapeValue
import com.example.compose.ui.theme.NotoSans

data class WordItem(val word: String, val mean: String)
@Composable
fun CardEjemplos(modifier: Modifier = Modifier, words: List<WordItem>){
    Column(
        modifier = Modifier
            .width(360.dp).heightIn(100.dp, 360.dp)
            .padding(10.dp)
            .background(
                color = colorResource(R.color.card_secundaria),
                shape = RoundedCornerShape(roundedCornerShapeValue)
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