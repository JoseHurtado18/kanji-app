package com.example.compose.kanji.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.compose.roundedCornerShapeValue
import com.example.compose.ui.theme.ComposeTheme
import com.example.compose.ui.theme.Roboto

@Composable
fun CardMnemotecnia(mnemonic: String,modifier: Modifier= Modifier){
    Column(
        modifier = Modifier
            .width(360.dp).heightIn(100.dp, 360.dp)
            .padding(10.dp)
            .background(
                color = colorResource(R.color.card_secundaria),
                shape = RoundedCornerShape(roundedCornerShapeValue)
            )
            .padding(15.dp)

    ){
        Row(
            modifier = modifier.fillMaxWidth()
                .padding(top = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)

        ){
            Image(
                painter = painterResource(R.drawable.ic_lightbulb),
                contentDescription = "lightbulb icono"
            )

            Text(
                text = "Mnemotecnia",
                color = colorResource(R.color.grisclaro),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold

            )
        }

        Text(
            text = mnemonic,
            color = Color.White,
            fontSize = 16.sp,
            fontFamily = Roboto,
            fontWeight = FontWeight.SemiBold,
            modifier = modifier.padding(top = 10.dp)
        )
    }
}

@Preview
@Composable
fun PrevCardExamples(){
    ComposeTheme() {
        CardMnemotecnia("Un niño (子) bajo un techo lleno de ideas revueltas: así se ve alguien estudiando duro en la escuela.")
    }
}