package com.example.compose.presentation.word.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SelectableChipColors
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.R
import com.example.compose.domain.model.TypeWord

import com.example.compose.presentation.kanji.add.FormLabel
import com.example.compose.presentation.word.components.BtnBuscar
import com.example.compose.presentation.word.components.CardReadings
import com.example.compose.presentation.word.components.CardWordOutput
import com.example.compose.presentation.word.components.OptionsTypeWord
import com.example.compose.presentation.word.components.WordInputField
import com.example.compose.presentation.word.components.WordKanjisComponent
import com.example.compose.ui.theme.ComposeTheme
import com.example.compose.ui.theme.NotoSans
import com.example.compose.ui.theme.Roboto
import com.example.compose.ui.theme.RobotoMono

@Composable
fun EditWordScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: EditWordViewModel,
) {
    LaunchedEffect(Unit) {
        viewModel.uiMessage.collect { message ->
            when (message) {
                is EditWordUiMessage.Info -> { /* Snackbar con message.text */ }
                is EditWordUiMessage.WordUpdated -> { onBack() }
            }
        }
    }

    Scaffold(
        backgroundColor = colorResource(R.color.background_base),
        topBar = { HeaderEditWord(onClick = viewModel::submit, onBack = onBack) },
        bottomBar = {}
    ) { contentPadding ->
        Column(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxSize()
                .background(colorResource(R.color.black))
                .padding(contentPadding)
                .padding(24.dp)
        ) {
            EditWordContentForm(viewModel = viewModel)
        }
    }
}

@Composable
fun EditWordContentForm(
    modifier: Modifier = Modifier,
    viewModel: EditWordViewModel
) {
    val state by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier.verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        CardWordOutput(word = state.kanjiWord, reading = state.reading)

        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom, // el botón queda alineado con el TextField, no con la etiqueta
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            WordInputField(
                title = "Palabra",
                value = state.kanjiWord,
                onValueChange = viewModel::onKanjiWordChange,
                placeholder = "Pega o escribe",
                modifier = Modifier.weight(1f)
            )

            BtnBuscar(
                onClick = viewModel::obtenerLecturasKanjisByWord,
                modifier = Modifier.size(56.dp) // mismo alto que el TextField y cuadrado
            )
        }

        WordInputField(
            title = "Lectura",
            value = state.reading,
            onValueChange = viewModel::onReadingChange,
            placeholder = "Escribe la lectura (hiragana)"
        )

        WordInputField(
            title = "Significado",
            value = state.meaning,
            onValueChange = viewModel::onMeaningChange,
            placeholder = "Escribe el significado"
        )

        WordKanjisComponent(
            lista = state.lecturas,
            onAdd = viewModel::addLectura,
            onValueChange = viewModel::onValueChange,
            onRemove = viewModel::removeLecturaAt
        )

        OptionsTypeWord(
            selected = state.selectedTypeWord,
            onOptionSelected = viewModel::onTypeWordChange
        )

        WordInputField(
            title = "Frase de ejemplo",
            value = state.sentence,
            onValueChange = viewModel::onSentenceChange,
            placeholder = "Escribe una frase de ejemplo",
            singleLine = false,
            minLines = 2
        )

        WordInputField(
            title = "Notas",
            value = state.notes,
            onValueChange = viewModel::onNotesChange,
            placeholder = "Escribe notas adicionales",
            singleLine = false,
            minLines = 2
        )

        state.errorMessage?.let { error ->
            Text(
                text = error,
                color = Color(0xFFFF6B6B),
                fontSize = 14.sp,
                fontFamily = Roboto
            )
        }
    }
}

@Composable
fun HeaderEditWord(
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
        IconButton(
            onClick = onBack,
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Icon(
                Icons.Default.Close,
                contentDescription = "icono cancelar editar palabra",
                tint = Color.White
            )
        }

        Text(
            text = "Editar palabra",
            color = Color.White,
            fontSize = 20.sp,
            fontFamily = Roboto,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(Alignment.Center)
        )

        Box(
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Text(
                "Guardar",
                color = colorResource(R.color.azul_title),
                fontSize = 18.sp,
                fontFamily = Roboto,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.clickable(onClick = onClick)
            )
        }
    }
}







@Preview
@Composable
fun PreviewEditWordScreen() {
    ComposeTheme {
        // EditWordScreen preview
    }
}
