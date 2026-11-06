package com.example.compose.presentation.training.exercises

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.presentation.training.components.BtnComponent
import com.example.compose.presentation.training.components.CardExercise
import com.example.compose.presentation.training.components.InputAnswer
import com.example.compose.ui.theme.ComposeTheme
import com.example.compose.ui.theme.NotoSans

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

