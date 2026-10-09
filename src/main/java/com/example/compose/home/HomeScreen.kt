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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.R
import com.example.compose.home.components.HeaderGlobal
import com.example.compose.home.components.HomeUiMessage
import com.example.compose.home.components.HomeViewModel
import com.example.compose.roundedCornerShapeValue
import com.example.compose.ui.theme.ComposeTheme
import com.example.compose.ui.theme.Roboto
import androidx.compose.runtime.getValue
@OptIn(ExperimentalGridApi::class)

@Composable
fun Main(viewModel: HomeViewModel,
         modifier: Modifier = Modifier) {
    val curva_banner = 50.dp

    LaunchedEffect(Unit) {
        viewModel.getLearnedKanjis()
        viewModel.uiMessage.collect { message ->
            when(message) {
                is HomeUiMessage.Info -> {}
            }
        }
    }

    // Contenedor principal para superponer capas
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // CAPA 1 (FONDO): Imagen y Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.banner_torii),
                contentDescription = "Imagen de tori para el banner",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(bottomStart = curva_banner, bottomEnd = curva_banner)),
                contentScale = ContentScale.Crop
            )
            Header(Modifier.padding(horizontal = 16.dp))
        }

        // CAPA 2 (FRENTE): Grids y Botón
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 1. Este Spacer empuja el primer grid hacia abajo.
            // Como la imagen mide 300dp y el grid 137dp, ponerlo a 230dp
            // hace que quede exactamente mitad sobre la imagen y mitad sobre lo negro.
            Spacer(modifier = Modifier.height(230.dp))

            // 2. Primer Grid (Solo 2 kvis montados en el borde)
            GridSuperpuesto(viewModel)

            // 3. Separación entre ambos grids
            Spacer(modifier = Modifier.height(24.dp))

            // 4. Segundo Grid (Los 4 kvis abajo)
            //GridKvis()

            // 5. Empuja el botón al final de la pantalla
            //Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(32.dp))
            EntrenarBtn(onClick = { Log.d("Filled button", "Filled button clicked.") })

            // Margen para que el botón no quede pegado al piso del teléfono
            Spacer(modifier = Modifier.height(32.dp))
        }
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
            color = Color.Black,
            fontWeight = FontWeight.SemiBold
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
        //Main()
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
fun GridSuperpuesto(viewModel: HomeViewModel,modifier: Modifier = Modifier) {
    val state by viewModel.uiState.collectAsState()
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Grid(
            config = {
                repeat(2) { column(167.dp) }
                repeat(2) { row(size = 137.dp) } // <-- Solo 1 fila
            }
        ) {
            Kvi(title = "Kanjis aprendidos", subtitle = state.kanjisLearned.toString(), image = R.drawable.ic_language_japanese, contDesc = "kanji logo")
            Kvi(title = "Por repasar", subtitle = "12", image = R.drawable.ic_schedule, contDesc = "reloj")
            Kvi(title = "Palabras aprendidas", subtitle = "420", image = R.drawable.ic_newsstand, contDesc = "palabras aprendidas")
            Kvi(title = "Tiempo de estudio", subtitle = "18 min", image = R.drawable.ic_hourglass, contDesc = "tiempo de estudio")
        }
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
                repeat(2) { row(size = 137.dp) }
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