package com.example.compose.kanji.presentation.add.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.R
import com.example.compose.kanji.domain.model.ExampleWord
import com.example.compose.kanji.presentation.add.FormLabel
import com.example.compose.ui.theme.ComposeTheme
import com.example.compose.ui.theme.Roboto
import com.example.compose.ui.theme.RobotoMono

@Composable
fun EjemplosComponent(
    lista: List<ExampleWord> = emptyList(),
    onAdd: () -> Unit,
    onValueChange: (index: Int, value: String) -> Unit,
    onRemove: (index: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            FormLabel("Ejemplos")
            BtnAdd(onClickAdd = onAdd)
        }
        Spacer(modifier = Modifier.height(6.dp))
        ListEjemplos(lista, onValueChange, onRemove)
    }
}

@Composable
fun ListEjemplos(
    lista: List<ExampleWord> = emptyList(),
    onValueChange: (index: Int, value: String) -> Unit,
    onRemove: (index: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        lista.forEachIndexed { index, ejemplo ->
            InputText(
                word = ejemplo.word,
                onValueChange = { newValue -> onValueChange(index, newValue) },
                onRemove = { onRemove(index) },
                placeholder = ""
            )
        }
    }
}

@Composable
fun InputText(
    word: String,
    onValueChange: (String) -> Unit,
    onRemove: () -> Unit,
    placeholder: String
) {
    val shape = RoundedCornerShape(15.dp)
    TextField(
        shape = shape,
        value = word,
        onValueChange = onValueChange,
        singleLine = true,
        trailingIcon = {
            Icon(
                Icons.Default.Close,
                contentDescription = "icono eliminar item",
                tint = colorResource(R.color.texto_secundario),
                modifier = Modifier.clickable(onClick = onRemove)
            )
        },
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

@Composable
fun BtnAdd(onClickAdd: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.clickable(onClick = onClickAdd)
    ) {
        Icon(Icons.Default.Add, contentDescription = "icono agregar", tint = colorResource(R.color.azul_title))
        Text(
            text = "Agregar",
            color = colorResource(R.color.azul_title),
            fontSize = 16.sp,
            fontFamily = Roboto,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview
@Composable
fun PreviewEjemplosComponent() {
    ComposeTheme {
        EjemplosComponent(
            lista = listOf(ExampleWord(word = "agua", reading = "みず", meaningEs = "agua")),
            onAdd = {},
            onValueChange = { _, _ -> },
            onRemove = {}
        )
    }
}