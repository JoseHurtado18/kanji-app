package com.example.compose.training.exercises

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.training.components.BtnComponent
import com.example.compose.training.components.CardExercise
import com.example.compose.training.components.InputAnswer
import com.example.compose.ui.theme.ComposeTheme
import com.example.compose.ui.theme.NotoSans
import com.example.compose.ui.theme.Roboto

//tipo9 : Palabara en kanji
@Composable
fun MeanWordCard(word: String, modifier: Modifier = Modifier) {
    val answerState = rememberTextFieldState("")
    Column(modifier = modifier.fillMaxWidth()) {
        CardExercise(word, "¿Cuál es el kanji?", Roboto,50)

        Spacer(modifier = Modifier.height(30.dp))

        InputAnswer(answerState, modifier)

        Spacer(modifier = Modifier.height(30.dp))

        BtnComponent(onClick = {}, "Comprobar")
    }
}

@Preview
@Composable
fun PreviewMeanWordCard(){
    ComposeTheme() {
        MeanWordCard("Extranjero")
    }
}