package com.example.compose.kanji.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.R
import com.example.compose.roundedCornerShapeValue

@Composable
fun CardEscritura(strokes: List<String>, modifier: Modifier = Modifier) {
    // Cada click en "Reproducir" cambia la clave y reinicia la animación
    var replayKey by remember { mutableIntStateOf(0) }

    Column(
        modifier = modifier
            .size(width = 360.dp, height = 360.dp)
            .padding(10.dp)
            .background(
                color = colorResource(R.color.card_secundaria),
                shape = RoundedCornerShape(roundedCornerShapeValue)
            )
            .padding(15.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Orden de escritura",
                color = colorResource(R.color.grisclaro),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )

            ReproducirBtn(onClick = { replayKey++ })
        }

        AnimatedKanjiCanvas(
            strokePaths = strokes,
            replayKey = replayKey,
            strokeColor = Color.White,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(top = 12.dp)
        )
    }
}

@Composable
fun ReproducirBtn(onClick: () -> Unit, modifier: Modifier = Modifier) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .width(135.dp)
            .height(35.dp),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, Color.DarkGray),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Black,
            contentColor = Color.White
        ),
        contentPadding = PaddingValues(start = 8.dp, end = 12.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_play),
            contentDescription = "play boton"
        )

        Text(
            text = "Reproducir",
            fontSize = 14.sp
        )
    }
}
