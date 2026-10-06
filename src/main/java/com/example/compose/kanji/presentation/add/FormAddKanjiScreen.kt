package com.example.compose.kanji.presentation.add

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.R
import com.example.compose.kanji.presentation.add.components.CaracterInput
import com.example.compose.kanji.presentation.add.components.EjemplosComponent
import com.example.compose.kanji.presentation.add.components.InputComponent
import com.example.compose.kanji.presentation.add.components.MeanInput
import com.example.compose.kanji.presentation.add.components.MnemotecniaInput
import com.example.compose.kanji.presentation.add.components.NotasInput
import com.example.compose.kanji.presentation.add.components.OptionsJplt
import com.example.compose.ui.theme.ComposeTheme
import com.example.compose.ui.theme.NotoSans
import com.example.compose.ui.theme.Roboto
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.navigation.NavController

@Composable
fun FormAddKanjiScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FormViewModel,

    ) {

    LaunchedEffect(Unit) {
        viewModel.uiMessage.collect { message ->
            when (message) {
                is FormKanjiUiMessage.Info -> { /* Snackbar con message.text */ }
                is FormKanjiUiMessage.KanjiAdded -> { onBack() }

            }
        }
    }

    Scaffold(
        backgroundColor = colorResource(R.color.background_base),
        topBar = {HeaderFormKanji(onClick = viewModel::submit,onBack = onBack)},
        bottomBar = {}
    ) {
        contentpadding ->
        Column(
            modifier = modifier.fillMaxWidth().
            fillMaxSize()
                .background(colorResource(R.color.black))
                .padding(contentpadding)
                .padding(24.dp)
        ) {
            ContentForm(viewModel = viewModel)
        }
    }

}

@Composable
fun ContentForm(
    modifier: Modifier = Modifier,
    viewModel: FormViewModel
) {
    val state by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier.verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(15.dp)

    ) {

        CardKanjiOutput(state.caracter, modifier)
        CaracterInput(state.caracter, viewModel::onCaracterChange,"Pega o escribe el kanji")
        MeanInput(state.significado,viewModel::onSignificadoChange, "Escribe el significado")

        Row(
            modifier = Modifier.fillMaxWidth(), // Quitado el height fijo de 70.dp
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            // Al pasar Modifier.weight(1f), cada uno ocupará el 50% exactamente
            InputComponent("On'yomi", state.onyomi, viewModel::onOnyomiChange, placeholder = "", modifier = Modifier.weight(1f))
            InputComponent("Kun'yomi", state.kunyomi, viewModel::onKunyomiChange, "", modifier = Modifier.weight(1f))
        }

        Row(
            modifier = Modifier.fillMaxWidth(), // Quitado el height fijo de 70.dp
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            // Al pasar Modifier.weight(1f), cada uno ocupará el 50% exactamente
            InputComponent("Radical", state.radical,viewModel::onRadicalChange, "", modifier = Modifier.weight(1f))
            InputComponent("Trazos", state.trazos, viewModel::onTrazosChange, "", modifier = Modifier.weight(1f))
        }

        OptionsJplt( selected = state.selectedJlptLevel,
            onOptionSelected = viewModel::onJlptLevelChange)
        EjemplosComponent(
            lista = state.exampleWords,
            onAdd = viewModel::addExampleWord,
            onValueChange = viewModel::onExampleWordChange,
            onRemove = viewModel::removeExampleWordAt
        )
        MnemotecniaInput(state.mnemotecnia,viewModel::onMnemotecniaChange, "")
        NotasInput(state.notas, viewModel::onNotasChange,"")
    }
}

@Composable
fun HeaderFormKanji(onClick: () -> Unit = {},modifier: Modifier = Modifier, onBack: () -> Unit = {}, ){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(8.dp)

    ) {
        // 1. Botón a la izquierda
        IconButton(
            onClick = onBack,
            modifier = Modifier.align(Alignment.CenterStart) // Alineado al inicio
        ) {
           Icon(
                Icons.Default.Close,
               contentDescription = "icono cancelar add kanji",
                tint = Color.White

            )
        }

        // 2. Texto en el centro exacto
        Text(
            text = "Agregar kanji",
            color = Color.White,
            fontSize = 20.sp,
            fontFamily = Roboto,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(Alignment.Center) // Alineado al centro absoluto del Box
        )

        // 3. Menú a la derecha
        // Lo envolvemos en un Box por si tu MinimalDropdownMenu no acepta el parámetro modifier
        Box(
            modifier = Modifier.align(Alignment.CenterEnd) // Alineado al final
        ) {
            Text("Guardar",
                color = colorResource(R.color.azul_title),
                fontSize = 18.sp,
                fontFamily = Roboto,
                fontWeight = FontWeight.SemiBold,
                modifier = modifier.clickable(onClick = onClick)
            )
        }
    }
}



@Composable
fun FormLabel(title: String, modifier: Modifier = Modifier){
    Text(
        text = title,
        fontSize = 16.sp,
        fontFamily = Roboto,
        fontWeight = FontWeight.SemiBold,
        color = colorResource(R.color.texto_progreso)
    )
}

@Composable
fun CardKanjiOutput(outPut: String, modifier: Modifier){
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = colorResource(R.color.grisdestacado)
            ),
            modifier = Modifier
                .size(width = 100.dp, height = 100.dp)
        ) {
            // Agregamos un Box que ocupa todo el Card para centrar el texto
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = outPut,
                    fontFamily = NotoSans,
                    color = colorResource(R.color.texto_secundario),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewFormAddKanjiScreen() {
    ComposeTheme() {
        //FormAddKanjiScreen(onBack = {})
    }
}