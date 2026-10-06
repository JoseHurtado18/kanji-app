package com.example.compose.training.exercises

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.R
import com.example.compose.roundedCornerShapeValue
import com.example.compose.training.components.BtnComponent
import com.example.compose.training.components.CardExercise
import com.example.compose.training.components.InputAnswer
import com.example.compose.ui.theme.ComposeTheme
import com.example.compose.ui.theme.NotoSans
import com.example.compose.ui.theme.Roboto

//tipo 1: Mostrar kanji

@Composable
fun RecallMeaningCard(kanji: String, modifier: Modifier = Modifier){
    val answerState = rememberTextFieldState(initialText = "")
    Column(modifier = modifier.fillMaxWidth()
    ) {

        CardExercise(kanji, "¿Qué significa?", fuente = NotoSans,90)

        Spacer(modifier = Modifier.height(30.dp))
        InputAnswer(answerState, modifier)
        Spacer(modifier = Modifier.height(30.dp))
        BtnComponent(onClick = {}, title = "Mostrar respuesta")
    }
}

@Preview(showBackground = false)
@Composable
fun PreviewRecallMeaningCard(){
    ComposeTheme() {
        RecallMeaningCard("水")
    }
}

