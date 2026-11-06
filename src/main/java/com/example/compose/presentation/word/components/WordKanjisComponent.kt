package com.example.compose.presentation.word.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
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
import com.example.compose.domain.model.KanjiReadingAPI
import com.example.compose.presentation.kanji.add.FormLabel
import com.example.compose.ui.theme.ComposeTheme
import com.example.compose.ui.theme.Roboto
import com.example.compose.ui.theme.RobotoMono

@Composable
fun WordKanjisComponent(
    lista: List<KanjiReadingAPI> = emptyList(),
    onAdd: () -> Unit = {},
    onValueChange: (index: Int, kanji: String, reading: String) -> Unit = { _, _, _ -> },
    onRemove: (index: Int) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            FormLabel("Kanjis")
            BtnAdd(onClickAdd = onAdd)
        }
        Spacer(modifier = Modifier.height(6.dp))
        ListKanjis(
            lista = lista,
            onValueChange = onValueChange,
            onRemove = onRemove
        )
    }
}

@Composable
fun ListKanjis(
    lista: List<KanjiReadingAPI> = emptyList(),
    onValueChange: (index: Int, kanji: String, reading: String) -> Unit = { _, _, _ -> },
    onRemove: (index: Int) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        lista.forEachIndexed { index, kanji ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                InputText(
                    word = kanji.kanji,
                    onValueChange = { newKanji -> onValueChange(index, newKanji, kanji.reading) },
                    placeholder = "",
                    modifier = Modifier.weight(1f)
                )
                InputText(
                    word = kanji.reading,
                    onValueChange = { newReading -> onValueChange(index, kanji.kanji, newReading) },
                    placeholder = "",
                    modifier = Modifier.weight(1f)
                )
                BtnDelete(
                    onClick = { onRemove(index) }
                )
            }
        }
    }
}

@Composable
fun InputText(
    word: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
    modifier: Modifier = Modifier,
    onRemove: () -> Unit = {}
) {
    val shape = RoundedCornerShape(15.dp)
    TextField(
        shape = shape,
        value = word,
        onValueChange = onValueChange,
        singleLine = true,
        textStyle = TextStyle(
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            fontFamily = RobotoMono,
        ),
        placeholder = {
            if (placeholder.isNotEmpty()) {
                Text(
                    text = placeholder,
                    fontFamily = RobotoMono,
                    fontSize = 18.sp,
                    color = colorResource(R.color.texto_terciario)
                )
            }
        },
        modifier = modifier
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
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "icono agregar",
            tint = colorResource(R.color.azul_title)
        )
        Text(
            text = "Agregar",
            color = colorResource(R.color.azul_title),
            fontSize = 16.sp,
            fontFamily = Roboto,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun BtnDelete(onClick: () -> Unit, modifier: Modifier = Modifier) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.size(56.dp),
        shape = RoundedCornerShape(15.dp),
        border = BorderStroke(1.dp, colorResource(R.color.border_outline_btn)),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = colorResource(R.color.grisdestacado),
            contentColor = Color.White
        ),
        contentPadding = PaddingValues(0.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "icono eliminar",
            tint = colorResource(R.color.texto_secundario),
            modifier = Modifier.size(24.dp)
        )
    }
}

@Preview
@Composable
fun PreviewEjemplosComponent() {
    ComposeTheme {
        WordKanjisComponent(
            lista = listOf(KanjiReadingAPI(kanji = "agua", reading = "みず")),
            onAdd = {},
            onValueChange = { _, _, _ -> },
            onRemove = {}
        )
    }
}
