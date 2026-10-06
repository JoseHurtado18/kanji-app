package com.example.compose.kanji.presentation.add.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.Text
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
import com.example.compose.ui.theme.Roboto
import com.example.compose.ui.theme.RobotoMono

@Composable
fun InputComponent(
    title: String,
    value:String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(8.dp)

    // Se le aplica el 'modifier' que recibe (el cual contiene el .weight(1f)) SOLO al Column
    Column(modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(
            text = title,
            fontSize = 16.sp,
            fontFamily = Roboto,
            fontWeight = FontWeight.SemiBold,
            color = colorResource(R.color.texto_progreso)
        )

        TextField(
            shape = shape,
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
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
            // IMPORTANTE: Usa Modifier con M mayúscula en lugar de reutilizar 'modifier'
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = colorResource(R.color.border_outline_btn),
                    shape = shape
                ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = colorResource(R.color.grisdestacado),
                unfocusedContainerColor = colorResource(R.color.grisdestacado),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
    }
}