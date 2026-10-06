package com.example.compose.training.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.compose.R
import com.example.compose.ui.theme.RobotoMono


@Composable
fun InputAnswer(state: TextFieldState, modifier: Modifier ) {
    TextField(
        state = state,
        lineLimits = TextFieldLineLimits.SingleLine,
        textStyle = TextStyle(color = Color.White,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            fontFamily = RobotoMono,
            textAlign = TextAlign.Center
        ),
        placeholder = {Text("Escribe tu respuesta",
            fontFamily = RobotoMono,
            fontSize = 18.sp,
            modifier = Modifier.fillMaxWidth(), // Permite que el Text ocupe el ancho del TextField
            textAlign = TextAlign.Center)},
        modifier = modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = colorResource(R.color.card_principal),  // Color cuando está seleccionado
            unfocusedContainerColor = colorResource(R.color.card_principal),     // Color cuando NO está seleccionado

            // Opcional: Si quieres ocultar la línea inferior por defecto del TextField
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}