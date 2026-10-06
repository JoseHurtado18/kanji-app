package com.example.compose.training.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.roundedCornerShapeValue


@Composable
fun BtnComponent(onClick: () -> Unit, title: String, modifier: Modifier = Modifier){
    Button(onClick = { onClick() }, modifier.fillMaxWidth().height(66.dp),
        shape = RoundedCornerShape(roundedCornerShapeValue),
        colors= ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black
        )){
        Text(text = title,
            fontSize = 22.sp)
    }
}