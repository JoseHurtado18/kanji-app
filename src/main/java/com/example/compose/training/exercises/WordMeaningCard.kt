package com.example.compose.training.exercises

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.R
import com.example.compose.training.components.BtnComponent
import com.example.compose.training.components.CardExercise
import com.example.compose.training.components.InputAnswer
import com.example.compose.ui.theme.ComposeTheme
import com.example.compose.ui.theme.NotoSans
import com.example.compose.ui.theme.RobotoMono


//tipo 8: escribir el significado de una palabra
@Composable
fun WordMeaningCard(word: String, modifier: Modifier = Modifier) {
    val answerState = rememberTextFieldState("")
    Column(modifier = modifier.fillMaxWidth()) {
        CardExercise(word, "¿Qué significa?", NotoSans,50)

        Spacer(modifier = Modifier.height(30.dp))

        InputAnswer(answerState, modifier)

        Spacer(modifier = Modifier.height(30.dp))

        BtnComponent(onClick = {}, "Comprobar")
    }
}

@Preview
@Composable
fun PreviewWordMeaning(){
    ComposeTheme() {
        WordMeaningCard("海外")
    }
}