package com.example.compose.kanji.presentation.add.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.R
import com.example.compose.kanji.presentation.add.FormLabel
import com.example.compose.ui.theme.RobotoMono

@Composable
fun NotasInput(notas:String, onValueChange: (String) -> Unit,placeholder: String, modifier: Modifier = Modifier) {
    val shape = RoundedCornerShape(8.dp)
    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        FormLabel("Notas")
        TextField(
            shape = RoundedCornerShape(8.dp),
            value = notas,
            onValueChange = onValueChange,
            textStyle = TextStyle(
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                fontFamily = RobotoMono,
            ),
            placeholder = {
                Text(
                    text = placeholder,
                    fontFamily = RobotoMono,
                    fontSize = 18.sp,
                )
            },
            modifier = modifier.fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = colorResource(R.color.border_outline_btn), // Cambia por el color que prefieras
                    shape = shape
                ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = colorResource(R.color.grisdestacado),  // Color cuando está seleccionado
                unfocusedContainerColor = colorResource(R.color.grisdestacado),     // Color cuando NO está seleccionado

                // Opcional: Si quieres ocultar la línea inferior por defecto del TextField
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
    }
}