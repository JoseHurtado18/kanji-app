package com.example.compose.presentation.library.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.R
import com.example.compose.domain.model.TypeWord
import com.example.compose.domain.model.Word

import com.example.compose.ui.theme.ComposeTheme
import com.example.compose.ui.theme.NotoSans

data class WordItem(val kanjiWord: String, val reading: String, val meaning: String)

@Composable
fun WordList(
    words: List<Word>,
    onWordClick: (Word) -> Unit = {},
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        items(words, key = { it.id }) { word ->
            WordItemRow(
                word = word,
                onClick = { onWordClick(word) }
            )
        }
    }
}

@Composable
fun WordItemRow(
    word: Word,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = word.kanjiWord,
                color = Color.White,
                fontSize = 24.sp,
                fontFamily = NotoSans,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = word.reading,
                color = colorResource(R.color.grisclaro),
                fontSize = 16.sp,
                fontFamily = NotoSans
            )

            // Badge del tipo de palabra (ej: verbo, sustantivo, adjetivo)
            word.typeWord?.let { type ->
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFF16325C))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = type.name.lowercase(),
                        color = Color(0xFF64B5F6),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Badge de "Repasar hoy"
            if (word.isDueForReview) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .border(1.dp, Color(0xFFF59E0B), RoundedCornerShape(12.dp))
                        .background(Color(0x14F59E0B))
                        .padding(horizontal = 10.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = "Repasar hoy",
                        color = Color(0xFFF59E0B),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = word.meaning.lowercase(),
            color = colorResource(R.color.grisclaro),
            fontSize = 15.sp,
            fontFamily = NotoSans
        )

        Spacer(modifier = Modifier.height(10.dp))

        HorizontalDivider(
            thickness = 1.dp,
            color = Color(0xFF262626)
        )
    }
}

@Preview
@Composable
fun PreviewWordList() {
    ComposeTheme {
        WordList(
            words = listOf(
                Word(1, "学校", "がっこう", "escuela", TypeWord.SUSTANTIVO),
                Word(2, "食べる", "たべる", "comer", TypeWord.VERBO, isDueForReview = true),
                Word(3, "先生", "せんせい", "profesor/a", TypeWord.SUSTANTIVO)
            )
        )
    }
}