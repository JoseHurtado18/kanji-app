package com.example.compose.kanji.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.example.compose.R
import com.example.compose.home.EntrenarBtn
import com.example.compose.kanji.domain.model.Kanji
import com.example.compose.roundedCornerShapeValue
import com.example.compose.ui.theme.ComposeTheme


@Composable
fun CardBtns(
    kanji: Kanji,
             onEditClic: (Kanji) -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .height(112.dp)
            .background(Color.Transparent)
            .padding(horizontal = 16.dp), // antes: start = 20.dp, end = 20.dp
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        EntrenarBtn(onClick = {}, modifier = Modifier
            .fillMaxWidth()
            .weight(1f))

        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // también cambié width(400.dp) → fillMaxWidth()
        ) {
            AccionBtn(onClick = {
                onEditClic(kanji)
                }, "Editar", R.drawable.ic_edit_square, "icono editar", modifier = Modifier.weight(1f))
            AccionBtn(onClick = {}, "Agregar palabra", R.drawable.ic_add, "icono añadir", modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun AccionBtn(onClick: () -> Unit, title: String, image: Int, contDesc: String, modifier: Modifier = Modifier) {
    OutlinedButton(
        onClick = onClick, // Asegúrate de pasar la variable onClick aquí
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = RoundedCornerShape(roundedCornerShapeValue),
        // Agregamos el borde grisáceo que se ve en la imagen
        border = BorderStroke(1.dp, colorResource(R.color.border_outline_btn)),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Black,
            contentColor = Color.White
        ),
        //contentPadding = PaddingValues(start = 8.dp, end = 12.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {

            // Los elementos se centrarán automáticamente
            Icon(
                painter = painterResource(image),
                contentDescription = contDesc,
                tint= colorResource(R.color.texto_boton_outline)
            )

            // Este Spacer crea el pequeño espacio entre el icono y el texto
            //Spacer(modifier = Modifier.width(6.dp))

            Text(
                text = title,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = colorResource(R.color.texto_boton_outline)
            )
        }
    }
}

@Preview
@Composable
fun PrevCardBtns(){
    ComposeTheme() {
        //CardBtns(onEditClic = {})
    }
}