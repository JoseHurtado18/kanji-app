package com.example.compose.presentation.word.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.R
import com.example.compose.presentation.kanji.detail.MinimalDropdownMenu
import com.example.compose.ui.theme.Roboto

@Composable
fun TopBarWord(onBack: () -> Unit = {}, modifier: Modifier = Modifier) {
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
            text = "Palabra",
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