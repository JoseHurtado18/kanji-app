package com.example.compose.kanji.presentation.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.R
import com.example.compose.kanji.presentation.add.CardKanjiOutput
import com.example.compose.kanji.presentation.add.components.CaracterInput
import com.example.compose.kanji.presentation.add.components.EjemplosComponent
import com.example.compose.kanji.presentation.add.components.InputComponent
import com.example.compose.kanji.presentation.add.components.MeanInput
import com.example.compose.kanji.presentation.add.components.MnemotecniaInput
import com.example.compose.kanji.presentation.add.components.NotasInput
import com.example.compose.kanji.presentation.add.components.OptionsJplt
import com.example.compose.ui.theme.Roboto

@Composable
fun EditScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EditViewModel,
) {

    LaunchedEffect(Unit) {
        viewModel.uiMessage.collect { message ->
            when (message) {
                is EditKanjiUiMessage.Info -> { /* Snackbar con message.text */ }
                is EditKanjiUiMessage.KanjiUpdated -> { onBack() }
            }
        }
    }

    Scaffold(
        backgroundColor = colorResource(R.color.background_base),
        topBar = { HeaderEditKanji(onClick = viewModel::submit, onBack = onBack) },
        bottomBar = {}
    ) { contentpadding ->
        Column(
            modifier = modifier.fillMaxWidth()
                .fillMaxSize()
                .background(colorResource(R.color.black))
                .padding(contentpadding)
                .padding(24.dp)
        ) {
            EditContentForm(viewModel = viewModel)
        }
    }
}

@Composable
fun EditContentForm(
    modifier: Modifier = Modifier,
    viewModel: EditViewModel
) {
    val state by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier.verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {

        CardKanjiOutput(state.caracter, modifier)
        CaracterInput(state.caracter, viewModel::onCaracterChange, "Pega o escribe el kanji")
        MeanInput(state.significado, viewModel::onSignificadoChange, "Escribe el significado")

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            InputComponent("On'yomi", state.onyomi, viewModel::onOnyomiChange, placeholder = "", modifier = Modifier.weight(1f))
            InputComponent("Kun'yomi", state.kunyomi, viewModel::onKunyomiChange, "", modifier = Modifier.weight(1f))
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            InputComponent("Radical", state.radical, viewModel::onRadicalChange, "", modifier = Modifier.weight(1f))
            InputComponent("Trazos", state.trazos, viewModel::onTrazosChange, "", modifier = Modifier.weight(1f))
        }

        OptionsJplt(
            selected = state.selectedJlptLevel,
            onOptionSelected = viewModel::onJlptLevelChange
        )
        EjemplosComponent(
            lista = state.exampleWords,
            onAdd = viewModel::addExampleWord,
            onValueChange = viewModel::onExampleWordChange,
            onRemove = viewModel::removeExampleWordAt
        )
        MnemotecniaInput(state.mnemotecnia, viewModel::onMnemotecniaChange, "")
        NotasInput(state.notas, viewModel::onNotasChange, "")
    }
}

@Composable
fun HeaderEditKanji(
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(8.dp)
    ) {
        // 1. Botón a la izquierda
        IconButton(
            onClick = onBack,
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Icon(
                Icons.Default.Close,
                contentDescription = "icono cancelar editar kanji",
                tint = Color.White
            )
        }

        // 2. Texto en el centro
        Text(
            text = "Editar kanji",
            color = Color.White,
            fontSize = 20.sp,
            fontFamily = Roboto,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(Alignment.Center)
        )

        // 3. Botón guardar a la derecha
        Box(
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Text(
                "Guardar",
                color = colorResource(R.color.azul_title),
                fontSize = 18.sp,
                fontFamily = Roboto,
                fontWeight = FontWeight.SemiBold,
                modifier = modifier.clickable(onClick = onClick)
            )
        }
    }
}
