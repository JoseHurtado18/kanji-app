package com.example.compose.presentation.word.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.R
import com.example.compose.roundedCornerShapeValue
import com.example.compose.ui.theme.ComposeTheme

@Composable
fun BtnBuscar(onClick: () -> Unit, modifier: Modifier = Modifier) {
    OutlinedButton(
        onClick = onClick, // Asegúrate de pasar la variable onClick aquí
        modifier = modifier
            .fillMaxHeight(),
        shape = RoundedCornerShape(roundedCornerShapeValue),
        // Agregamos el borde grisáceo que se ve en la imagen
        border = BorderStroke(1.dp, colorResource(R.color.border_outline_btn)),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Black,
            contentColor = Color.White
        ),
        //contentPadding = PaddingValues(start = 8.dp, end = 12.dp)
    ) {

        // Los elementos se centrarán automáticamente
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "icono buscar",
                tint = Color(0xFFFFB703),
                modifier = Modifier.size(40.dp)
            )



    }
}



@Preview
@Composable
fun BtnPreview(){
    ComposeTheme() {
        BtnBuscar(onClick = {})
    }
}