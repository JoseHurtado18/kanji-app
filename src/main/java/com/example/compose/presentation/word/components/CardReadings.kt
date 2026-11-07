package com.example.compose.presentation.word.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import com.example.compose.domain.model.KanjiReadingAPI
import com.example.compose.domain.model.Word
import com.example.compose.roundedCornerShapeValue
import com.example.compose.ui.theme.NotoSans
import kotlin.collections.forEach


@Composable
fun CardReadings(modifier: Modifier = Modifier, kanjis: List<KanjiReadingAPI>){
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
            text = "Lectura kanjis que la componen",
            color = colorResource(R.color.grisclaro),
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold

        )

        kanjis.forEach { item ->
            Row(
                modifier = modifier.fillMaxWidth()
                    .padding(top = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween

            ) {
                Row(verticalAlignment = Alignment.CenterVertically,) {
                    Text(
                        text = item.kanji,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontFamily = NotoSans
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = item.reading,
                        color = colorResource(R.color.grisclaro),
                        fontSize = 10.sp,
                        fontFamily = NotoSans
                    )
                }

                /*Text(
                    text = item.meaning,
                    color = colorResource(R.color.grisclaro),
                    fontSize = 16.sp,
                    fontFamily = NotoSans
                )*/
            }
        }


    }
}

/*@Composable
fun WordReadingComponent(
    lista: List<KanjiReadingAPI> = emptyList(),
    onValueChange: (index: Int, kanji: String, reading: String) -> Unit = { _, _, _ -> },
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            FormLabel("Kanjis")
        }
        Spacer(modifier = Modifier.height(6.dp))
        ListKanjisReadings(
            lista = lista,
            onValueChange = onValueChange,
        )
    }
}

@Composable
fun ListKanjisReadings(
    lista: List<KanjiReadingAPI> = emptyList(),
    onValueChange: (index: Int, kanji: String, reading: String) -> Unit = { _, _, _ -> },
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
                InputTextReading(
                    word = kanji.kanji,
                    onValueChange = { newKanji -> onValueChange(index, newKanji, kanji.reading) },
                    placeholder = "",
                    modifier = Modifier.weight(1f)
                )
                InputTextReading(
                    word = kanji.reading,
                    onValueChange = { newReading -> onValueChange(index, kanji.kanji, newReading) },
                    placeholder = "",
                    modifier = Modifier.weight(1f)
                )

            }
        }
    }
}

@Composable
fun InputTextReading(
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





@Preview
@Composable
fun PreviewEjemplosComponent2() {
    ComposeTheme {
        WordReadingComponent(
            lista = listOf(KanjiReadingAPI(kanji = "agua", reading = "みず")),
            onValueChange = { _, _, _ -> },
        )
    }
}*/
