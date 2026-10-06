package com.example.compose.kanji.presentation.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose.kanji.domain.model.ExampleWord
import com.example.compose.kanji.domain.model.JlptLevel
import com.example.compose.kanji.domain.model.Kanji
import com.example.compose.kanji.domain.usecase.KanjiUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject

/**
 * Estado UI del formulario de creación/edición de kanji.
 * Cada campo del formulario vive aquí, no en `remember` del Composable,
 * para que sobreviva a recomposiciones/rotación y para que la validación
 * y el envío puedan leer un único estado consistente.
 */
data class FormKanjiUiState(
    val caracter: String = "",
    val significado: String = "agua",
    val onyomi: String = "",
    val kunyomi: String = "",
    val radical: String = "",
    val trazos: String = "",
    val mnemotecnia: String = "",
    val notas: String = "",
    val selectedJlptLevel: JlptLevel? = null,
    val exampleWords: List<ExampleWord> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
) {
    /** Validación mínima antes de habilitar el botón de guardar. */
    val isValid: Boolean
        get() = caracter.isNotBlank() && significado.isNotBlank()
}

sealed interface FormKanjiUiMessage {
    data class Info(val text: String) : FormKanjiUiMessage
    data class KanjiAdded(val kanjiId: Int) : FormKanjiUiMessage
}

@HiltViewModel
class FormViewModel @Inject constructor(
    private val kanjiUseCases: KanjiUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(FormKanjiUiState())
    val uiState: StateFlow<FormKanjiUiState> = _uiState.asStateFlow()

    private val _uiMessage = Channel<FormKanjiUiMessage>(Channel.BUFFERED)
    val uiMessage = _uiMessage.receiveAsFlow()

    // --- Actualización de cada campo (llamado desde onValueChange en la Vista) ---

    fun onCaracterChange(value: String) {
        _uiState.update { it.copy(caracter = value, errorMessage = null) }
    }

    fun onSignificadoChange(value: String) {
        _uiState.update { it.copy(significado = value, errorMessage = null) }
    }

    fun onOnyomiChange(value: String) {
        _uiState.update { it.copy(onyomi = value) }
    }

    fun onKunyomiChange(value: String) {
        _uiState.update { it.copy(kunyomi = value) }
    }

    fun onRadicalChange(value: String) {
        _uiState.update { it.copy(radical = value) }
    }

    fun onTrazosChange(value: String) {
        _uiState.update { it.copy(trazos = value) }
    }

    fun onMnemotecniaChange(value: String) {
        _uiState.update { it.copy(mnemotecnia = value) }
    }

    fun onNotasChange(value: String) {
        _uiState.update { it.copy(notas = value) }
    }

    fun onJlptLevelChange(level: JlptLevel?) {
        _uiState.update { it.copy(selectedJlptLevel = level) }
    }

    /** Agrega una fila vacía de ejemplo; el usuario la completa luego en el TextField. */
    fun addExampleWord() {
        _uiState.update {
            it.copy(exampleWords = it.exampleWords + ExampleWord(word = "", reading = "", meaningEs = ""))
        }
    }

    /** Actualiza el texto del ejemplo en la posición `index`. */
    fun onExampleWordChange(index: Int, value: String) {
        _uiState.update { state ->
            val updated = state.exampleWords.toMutableList()
            if (index in updated.indices) {
                updated[index] = updated[index].copy(word = value)
            }
            state.copy(exampleWords = updated)
        }
    }

    /** Elimina el ejemplo en la posición `index`. */
    fun removeExampleWordAt(index: Int) {
        _uiState.update { state ->
            val updated = state.exampleWords.toMutableList()
            if (index in updated.indices) {
                updated.removeAt(index)
            }
            state.copy(exampleWords = updated)
        }
    }

    /** Restablece el formulario tras un guardado exitoso o al cancelar. */
    fun resetForm() {
        _uiState.value = FormKanjiUiState()
    }

    /**
     * Construye el `Kanji` a partir del estado actual y lo guarda.
     * La Vista solo necesita llamar a `viewModel.submit()`.
     */
    fun submit() {
        val current = _uiState.value

        if (!current.isValid) {
            _uiState.update { it.copy(errorMessage = "El carácter y el significado son obligatorios") }
            return
        }

        val kanji = Kanji(
            character = current.caracter,
            meaningEs = current.significado,
            onYomi = current.onyomi,
            kunYomi = current.kunyomi,
            radical = current.radical,
            strokeCount = current.trazos.toIntOrNull() ?: 0,
            mnemonic = current.mnemotecnia,
            notes = current.notas,
            jlptLevel = current.selectedJlptLevel,
            exampleWords = current.exampleWords,
            dateAdded = LocalTime.now()
        )

        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true, errorMessage = null) }
                val newId = kanjiUseCases.addKanji(kanji)
                _uiState.update { it.copy(isLoading = false) }
                _uiMessage.send(FormKanjiUiMessage.KanjiAdded(newId))
                _uiMessage.send(FormKanjiUiMessage.Info("Kanji agregado exitosamente"))
                resetForm()
            } catch (e: Exception) {
                val message = e.localizedMessage ?: "Error al agregar kanji"
                _uiState.update { it.copy(isLoading = false, errorMessage = message) }
                _uiMessage.send(FormKanjiUiMessage.Info(message))
            }
        }
    }
}