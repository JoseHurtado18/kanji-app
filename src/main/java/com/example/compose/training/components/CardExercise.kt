package com.example.compose.training.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.test.espresso.base.Default
import com.example.compose.R
import com.example.compose.roundedCornerShapeValue
import com.example.compose.ui.theme.NotoSans
import com.example.compose.ui.theme.Roboto
import androidx.compose.ui.text.font.FontFamily
import com.example.compose.ui.theme.CourierPrime
import com.example.compose.ui.theme.RobotoMono


@Composable
fun CardExercise(title: String, subTitle: String, fuente: FontFamily = FontFamily.Default, titleSize: Int, modifier: Modifier = Modifier){


        Column(
            modifier = modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(
                    color = colorResource(R.color.grisdestacado),
                    shape = RoundedCornerShape(roundedCornerShapeValue)
                )
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center // Centra el contenido agrupado
        ) {
            Text(
                text = title, // En la imagen es 貓, pero mantenemos tu texto
                fontFamily = fuente,
                fontSize = titleSize.sp,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(20.dp)) // Controla el espacio entre el kanji y la flecha

            Image(
                painter = painterResource(R.drawable.ic_arrow_downward),
                contentDescription = "flecha downward",
                // Si la flecha nativa es negra, puedes teñirla de gris para igualar la imagen:
                // colorFilter = ColorFilter.tint(Color.Gray)
            )

            Spacer(modifier = Modifier.height(20.dp)) // Controla el espacio entre la flecha y el texto

            Text(
                text = subTitle,
                fontFamily = RobotoMono,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                color = colorResource(R.color.texto_progreso)
            )
        }



}