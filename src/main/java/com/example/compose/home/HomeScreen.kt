package com.example.compose.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalGridApi
import androidx.compose.foundation.layout.Grid
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.R
import com.example.compose.home.components.HeaderGlobal
import com.example.compose.roundedCornerShapeValue
import com.example.compose.ui.theme.ComposeTheme
import com.example.compose.ui.theme.Roboto

@Composable
fun Main(modifier: Modifier = Modifier){
    Column(modifier =  Modifier.fillMaxWidth()
        .fillMaxHeight()
        .background(Color.Black)
        .padding(24.dp)) {
        HeaderGlobal( "Miercoles, 12 de agosto", "Buenos dias", "")

        GridKvis()
        EntrenarBtn(onClick = { Log.d("Filled button", "Filled button clicked.") })
    }
}

@Composable
fun Header(modifier: Modifier = Modifier){
    Column(
        modifier= modifier.size(width = 400.dp, height = 100.dp)
            .padding(top = 30.dp)
    ) {
        Text(
            text = "Miercoles, 12 de agosto",
            fontSize = 15.sp,
            color = Color.Gray
        )
        Text(
            text = "Buenos dias",
            fontSize = 32.sp,
            color = Color.White,
            fontFamily = Roboto,
            fontWeight = FontWeight.Bold

        )

    }
}



@Preview(showBackground = true)
@Composable
fun MainPreview(){
    ComposeTheme{
        Main()
    }
}

@Composable
fun Kvi(title: String, subtitle: String, image: Int, contDesc: String ,modifier: Modifier = Modifier){
    val height = 137.dp
    Column(
        verticalArrangement = Arrangement.Center,
        modifier =  Modifier.size(width = 167.dp, height = height)
            .padding(8.dp)
            .background(color = colorResource(R.color.kvinegro) ,
                shape = RoundedCornerShape(8.dp))
            .padding(15.dp)) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(1f)) {

            Image(painter = painterResource(id = image), contentDescription = contDesc)
            Text(
                text = title,
                color = Color.Gray,
                fontSize = 15.sp,
                modifier = Modifier.fillMaxWidth().weight(1f)
            )
        }

        Text(
            text = subtitle,
            color = Color.White,
            fontSize = 32.sp,
            modifier = Modifier.weight(1f).padding(top = 4.dp)
        )
    }
}

@OptIn(ExperimentalGridApi::class)
@Composable
fun GridKvis(modifier: Modifier = Modifier){

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Grid(
            config = {
                repeat(2) { column(167.dp) }
                repeat(3) { row(size = 137.dp) }
            },
            modifier = Modifier.padding(top = 30.dp)
        ) {
            Kvi(title = "Kanjis aprendidos", subtitle = "150", image = R.drawable.ic_language_japanese, contDesc = "kanji logo")
            Kvi(title = "Por repasar", subtitle = "12", image = R.drawable.ic_schedule, contDesc = "reloj")
            Kvi(title = "Palabras aprendidas", subtitle = "420", image = R.drawable.ic_newsstand, contDesc = "palabras aprendidas")
            Kvi(title = "Tiempo de estudio", subtitle = "18 min", image = R.drawable.ic_hourglass, contDesc = "tiempo de estudio")
        }
    }
}



@Composable
fun EntrenarBtn(onClick: () -> Unit, modifier: Modifier = Modifier){
    Box(modifier.size(width = 400.dp, height = 56.dp)){
        Button(onClick = { onClick() }, modifier.fillMaxWidth().height(48.dp),
            shape = RoundedCornerShape(roundedCornerShapeValue),
            colors= ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            )){
            Text("Entrenar",
                fontSize = 22.sp)
        }
    }

}