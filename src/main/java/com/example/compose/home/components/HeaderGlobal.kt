package com.example.compose.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.ui.theme.ComposeTheme
import com.example.compose.ui.theme.Roboto

@Composable
fun HeaderGlobal(fecha: String, title: String, subtitle: String,modifier: Modifier = Modifier ){
    Column(
        verticalArrangement = Arrangement.Center,
        modifier= modifier.size(width = 400.dp, height = 100.dp)
            .padding(top = 30.dp)
    ) {
        Text(
            text = fecha,
            fontSize = 15.sp,
            color = Color.Gray,
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Text(
                text = title,
                fontSize = 32.sp,
                color = Color.White,
                fontFamily = Roboto,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = subtitle,
                fontSize = 18.sp,
                color = Color.Gray
            )
        }


    }
}

@Preview
@Composable
fun PreviewHeaderG(){
    ComposeTheme(){
        HeaderGlobal( "Miercoles, 12 de agosto", "Buenos dias", "156 kanjis")
    }
}