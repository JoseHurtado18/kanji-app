package com.example.compose.presentation.word.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.R
import com.example.compose.domain.model.Word
import com.example.compose.roundedCornerShapeValue

@Composable
fun MeanWordCard(word: Word,modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .size(width = 360.dp, height = 100.dp)
            .fillMaxWidth()
            .padding(10.dp)
            .background(
                color = colorResource(R.color.grisdestacado),
                shape = RoundedCornerShape(roundedCornerShapeValue)
            )
            .padding(15.dp),
        verticalAlignment = Alignment.CenterVertically



    ) {
        val text = word.meaning
        val textoFormateado = text.replaceFirstChar { it.uppercase() }
        Column() {
            Text(
                text = textoFormateado,
                fontSize = 24.sp,
                color = Color.White
            )
            Text(
                text = "Significado principal",
                fontSize = 16.sp,
                color = colorResource(R.color.grisclaro)
            )
        }
        Spacer(modifier = Modifier.weight(1f))

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

    }
}