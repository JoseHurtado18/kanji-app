package com.example.compose.stats.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalGridApi
import androidx.compose.foundation.layout.Grid
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.R
import com.example.compose.ui.theme.ComposeTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import com.example.compose.roundedCornerShapeValue



@OptIn(ExperimentalGridApi::class)
@Composable
fun GridStats(dias: Int, presicion: Int,modifier: Modifier = Modifier){
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
    ) {
        Grid(
            config = {
                repeat(2) { column(170.dp) }
                repeat(1) { row(size = 137.dp) }
                gap(5.dp)
            },

            modifier = Modifier.padding(top = 30.dp)
        ) {
            Box(
                modifier = Modifier
                    .gridItem(row = 1, column = 1)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                KviRacha(dias)
            }
            Box(
                modifier = Modifier
                    .gridItem(row = 1, column = 2)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                KviPrecision( presicion,)
            }

        }
    }
}

@Preview
@Composable
fun PreviewGrid(){
    ComposeTheme() {
        GridStats(12, 87)
    }
}

val widthKvi= 160.dp
val height = 137.dp



@Composable
fun KviRacha(dias: Int, modifier: Modifier= Modifier){
    Column(
        verticalArrangement = Arrangement.Center,
        modifier =  Modifier.fillMaxWidth()
            .height(height)
            .padding(8.dp)
            .background(color = colorResource(R.color.grisdestacado) ,
                shape = RoundedCornerShape(roundedCornerShapeValue))
            .padding(15.dp)) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)) {

            Image(painter = painterResource(R.drawable.ic_mode_heat), contentDescription = "icono de racha")
            Text(
                text = "Racha",
                color = Color.Gray,
                fontSize = 15.sp,
                modifier = Modifier.fillMaxWidth().weight(1f)
            )
        }

        Row(
            // Ya no necesitas verticalAlignment aquí, alignByBaseline se encarga de eso.
            modifier = Modifier.padding(top = 4.dp)
        ) {
            Text(
                text = dias.toString(), // Aquí va el número ("12")
                color = Color.White,
                fontSize = 32.sp,
                modifier = Modifier.alignByBaseline() // Alinea por la base de la fuente
            )

            Text(
                text = "  dias", // Agregamos un espacio inicial para separarlo del número
                color = Color.Gray,
                fontSize = 15.sp,
                modifier = Modifier.alignByBaseline() // Alinea por la base de la fuente
            )
        }


    }
}

@Composable
fun KviPrecision(presicion: Int, modifier: Modifier = Modifier){
    Column(
        verticalArrangement = Arrangement.Center,
        modifier =  Modifier.fillMaxWidth()
            .height(height)
            .padding(8.dp)
            .background(color = colorResource(R.color.grisdestacado) ,
                shape = RoundedCornerShape(roundedCornerShapeValue))
            .padding(15.dp)) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)) {

            Image(painter = painterResource(R.drawable.ic_target), contentDescription = "icono de target")
            Text(
                text = "Precision",
                color = Color.Gray,
                fontSize = 15.sp,
                modifier = Modifier.fillMaxWidth().weight(1f)
            )
        }

        Row(
            // Ya no necesitas verticalAlignment aquí, alignByBaseline se encarga de eso.
            modifier = Modifier.padding(top = 4.dp)
        ) {
            Text(
                text = presicion.toString()+"%", // Aquí va el número ("12")
                color = Color.White,
                fontSize = 32.sp,
                modifier = Modifier.alignByBaseline() // Alinea por la base de la fuente
            )


        }


    }
}

