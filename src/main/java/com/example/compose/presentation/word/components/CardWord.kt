package com.example.compose.presentation.word.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
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
import com.example.compose.ui.theme.NotoSans

@Composable
fun CardWord(word: String, reading: String, score: Int, modifier: Modifier = Modifier) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp)
    ) {
        Text(
            text = word,
            fontFamily = NotoSans,
            color = Color.White,
            fontSize = 50.sp,
            fontWeight = FontWeight.SemiBold

        )

        Text(
            text = reading,
            color = colorResource(R.color.grisclaro),
            fontFamily = NotoSans,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row {
            for (i in 1..score) {
                ScoreWord()
            }
        }

        Text(
            text = "En proceso de dominio",
            color = colorResource(R.color.grisclaro)
        )

    }
}

@Composable
fun ScoreWord() {
    Icon(
        Icons.Default.Star,
        contentDescription = "star score",
        tint = colorResource(R.color.grisclaro)
    )
}