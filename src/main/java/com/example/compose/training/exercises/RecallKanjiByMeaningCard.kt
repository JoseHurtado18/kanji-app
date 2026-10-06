package com.example.compose.training.exercises;

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.training.components.BtnComponent
import com.example.compose.training.components.CardExercise
import com.example.compose.training.components.FingerDrawingCanvas
import com.example.compose.training.components.OptionsButton
import com.example.compose.training.components.radioOptions
import com.example.compose.ui.theme.ComposeTheme
import com.example.compose.ui.theme.Roboto


//Tipo 2: Escoger el kanji correcto en base a una palabra
@Composable
fun RecallKanjiByMeaningCard(word: String, modifier: Modifier = Modifier){
    Column(modifier = modifier.fillMaxWidth()) {
        CardExercise(word, "Escribe el kanji" , fuente = Roboto, 35)
        Spacer(modifier = Modifier.height(30.dp))
        OptionsButton(radioOptions)
        Spacer(modifier = Modifier.height(30.dp))

        BtnComponent(onClick = {}, "Comprobar")
    }
}

@Preview
@Composable
fun PreviewRecallKanjiByMeaningCard(){
    ComposeTheme() {
        RecallKanjiByMeaningCard("Gato")
    }
}