package com.example.compose.presentation.word.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.compose.presentation.kanji.add.FormLabel
import com.example.compose.ui.theme.RobotoMono

@Composable
fun WordInputField(
    title: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
    minLines: Int = 1
) {
    val shape = RoundedCornerShape(8.dp)

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        FormLabel(title)

        TextField(
            shape = shape,
            value = value,
            onValueChange = onValueChange,
            singleLine = singleLine,
            minLines = minLines,
            textStyle = TextStyle(
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                fontFamily = RobotoMono,
            ),
            placeholder = {
                Text(
                    text = placeholder,
                    fontFamily = RobotoMono,
                    fontSize = 18.sp,
                )
            },
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