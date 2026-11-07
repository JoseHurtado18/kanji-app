package com.example.compose.presentation.library.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.R
import com.example.compose.domain.model.Kanji
import com.example.compose.roundedCornerShapeValue
import com.example.compose.ui.theme.ComposeTheme
import com.example.compose.ui.theme.NotoSans
import com.example.compose.ui.theme.Roboto

data class KanjiItem(
    val kanji: String,
    val mean: String,
    val reading: String = ""
)

fun Kanji.toKanjiItem(): KanjiItem = KanjiItem(
    kanji = character,
    mean = meaningEs.split(",", "/").firstOrNull()?.trim()?.lowercase() ?: meaningEs.lowercase(),
    reading = onYomi.split("/").firstOrNull()?.trim() ?: onYomi
)

val kanjisList = listOf(
    KanjiItem(kanji = "学", mean = "estudio", reading = "ガク"),
    KanjiItem(kanji = "校", mean = "escuela", reading = "コウ"),
    KanjiItem(kanji = "学", mean = "estudio", reading = "ガク"),
    KanjiItem(kanji = "校", mean = "escuela", reading = "コウ"),
    KanjiItem(kanji = "学", mean = "estudio", reading = "ガク"),
    KanjiItem(kanji = "校", mean = "escuela", reading = "コウ"),
)

@Composable
fun KanjisWordCard(
    list: List<Kanji> = emptyList(),
    //onKanjiClick: (Kanji) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
            .background(
                color = colorResource(R.color.grisdestacado),
                shape = RoundedCornerShape(roundedCornerShapeValue)
            )
            .padding(16.dp),
    ) {
        Text(
            text = "Kanjis aprendidos que la componen",
            color = colorResource(R.color.grisclaro),
            fontFamily = Roboto,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(14.dp))

        if (list.size == 1) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ItemKanji(
                    item = list[0].toKanjiItem(),
                    //onClick = { onKanjiClick(item) },
                    modifier = Modifier.weight(1f)
                )
            }
        } else if (list.size >= 2) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                list.chunked(2).forEach { rowItems ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        rowItems.forEach { kanji ->
                            ItemKanji(
                                item = kanji.toKanjiItem(),
                                modifier = Modifier.weight(1f)
                            )
                        }
                        if (rowItems.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        } else {
            Text(
                text = "Sin kanjis asociados",
                color = colorResource(R.color.grisclaro),
                fontFamily = Roboto,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun ItemKanji(
    item: KanjiItem,
    //onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier
            //.clickable(onClick = onClick)
            .height(56.dp)
            .border(
                width = 1.dp,
                color = colorResource(R.color.border_outline_btn),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 8.dp)
    ) {
        Text(
            text = item.kanji,
            fontFamily = NotoSans,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {
            if (item.reading.isNotBlank()) {
                Text(
                    text = item.reading,
                    fontFamily = NotoSans,
                    fontSize = 12.sp,
                    color = colorResource(R.color.grisclaro),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Text(
                text = item.mean,
                fontFamily = Roboto,
                fontSize = 13.sp,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
fun PreviewKanjisWordCard() {
    ComposeTheme {
        KanjisWordCard()
    }
}
