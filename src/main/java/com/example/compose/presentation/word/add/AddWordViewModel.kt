package com.example.compose.presentation.word.add

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose.domain.model.KanjiReadingAPI
import com.example.compose.domain.model.TypeWord
import com.example.compose.domain.model.Word
import com.example.compose.domain.usecase.GetWordKanjiReadingsUseCase
import com.example.compose.domain.usecase.WordUseCases
import com.example.compose.presentation.word.edit.FormWordUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AddFormWordUiState(
    val kanjiWord: String = "",
    val reading: String = "",
    val meaning: String = "",
    val selectedTypeWord: TypeWord? = null,
    val sentence: String = "",
    val notes: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val lecturas: List<KanjiReadingAPI> = emptyList()
) {
    val isValid: Boolean
        get() = kanjiWord.isNotBlank() && meaning.isNotBlank()
}


sealed interface AddWordUiMessage {
    data class Info(val text: String) : AddWordUiMessage
    data class WordAdded(val text: String) : AddWordUiMessage
}

@HiltViewModel
class AddWordViewModel @Inject constructor(
    private val wordUseCases: WordUseCases,
    private val getWordKanjiReadings: GetWordKanjiReadingsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val _uiState = MutableStateFlow(AddFormWordUiState())
    val uiState: StateFlow<AddFormWordUiState> = _uiState.asStateFlow()

    private val _uiMessage = Channel<AddWordUiMessage>(Channel.BUFFERED)
    val uiMessage = _uiMessage.receiveAsFlow()

    private var originalIsDueForReview: Boolean = false

    init {

    }

    fun obtenerLecturasKanjisByWord() {
        viewModelScope.launch {
            getWordKanjiReadings(_uiState.value.kanjiWord)
                .onSuccess { list ->
                    // 1. Usar Log en lugar de println para mejor depuración en Android
                    Log.d("LecturasKanji", "Resultados obtenidos: $list")

                    // 2. Actualizar el estado para que la UI en Compose se redibuje
                    // Asumiendo que usas MutableStateFlow:
                    _uiState.value = _uiState.value.copy(
                        lecturas = list,
                        isLoading = false
                    )
                }
                .onFailure { error ->
                    // 3. Registrar el error en el Logcat
                    Log.e("LecturasKanji", "Error obteniendo lecturas", error)

                    // 4. Actualizar el estado para mostrar un mensaje de error en pantalla
                    _uiState.value = _uiState.value.copy(
                        errorMessage = "No se pudieron cargar las lecturas",
                        isLoading = false
                    )
                }
        }
    }
    fun onKanjiWordChange(value: String) {
        _uiState.update { it.copy(kanjiWord = value, errorMessage = null) }
    }

    fun onReadingChange(value: String) {
        _uiState.update { it.copy(reading = value) }
    }

    fun onMeaningChange(value: String) {
        _uiState.update { it.copy(meaning = value, errorMessage = null) }
    }

    fun onTypeWordChange(type: TypeWord?) {
        _uiState.update { it.copy(selectedTypeWord = type) }
    }

    fun onSentenceChange(value: String) {
        _uiState.update { it.copy(sentence = value) }
    }

    fun onNotesChange(value: String) {
        _uiState.update { it.copy(notes = value) }
    }

    /** Actualiza tanto el kanji como la lectura en la posición `index` de la lista de lecturas. */
    fun onValueChange(index: Int, kanji: String, reading: String) {
        _uiState.update { state ->
            val updated = state.lecturas.toMutableList()
            if (index in updated.indices) {
                updated[index] = updated[index].copy(kanji = kanji, reading = reading)
            }
            state.copy(lecturas = updated)
        }
    }

    /** Actualiza solo el kanji en la posición `index`. */
    fun onKanjiChange(index: Int, kanji: String) {
        _uiState.update { state ->
            val updated = state.lecturas.toMutableList()
            if (index in updated.indices) {
                updated[index] = updated[index].copy(kanji = kanji)
            }
            state.copy(lecturas = updated)
        }
    }

    /** Actualiza solo la lectura en la posición `index`. */
    fun onLecturaReadingChange(index: Int, reading: String) {
        _uiState.update { state ->
            val updated = state.lecturas.toMutableList()
            if (index in updated.indices) {
                updated[index] = updated[index].copy(reading = reading)
            }
            state.copy(lecturas = updated)
        }
    }

    /** Agrega una nueva fila vacía a la lista de kanjis/lecturas. */
    fun addLectura() {
        _uiState.update { state ->
            state.copy(lecturas = state.lecturas + KanjiReadingAPI(kanji = "", reading = ""))
        }
    }

    /** Elimina el kanji/lectura en la posición `index`. */
    fun removeLecturaAt(index: Int) {
        _uiState.update { state ->
            val updated = state.lecturas.toMutableList()
            if (index in updated.indices) {
                updated.removeAt(index)
            }
            state.copy(lecturas = updated)
        }
    }

    fun submit() {
        val current = _uiState.value

        if (!current.isValid) {
            _uiState.update { it.copy(errorMessage = "La palabra y el significado son obligatorios") }
            return
        }

        val validReadings = current.lecturas.filter {
            it.kanji.isNotBlank() || it.reading.isNotBlank()
        }

        val word = Word(
            kanjiWord = current.kanjiWord.trim(),
            reading = current.reading.trim(),
            meaning = current.meaning.trim(),
            typeWord = current.selectedTypeWord,
            sentence = current.sentence.ifBlank { null },
            notes = current.notes.ifBlank { null },
            isDueForReview = originalIsDueForReview,
            readings = validReadings
        )

        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true, errorMessage = null) }
                wordUseCases.addWordUseCase(word)
                _uiState.update { it.copy(isLoading = false) }
                _uiMessage.send(AddWordUiMessage.WordAdded("Palabra guardada exitosamente"))
            } catch (e: Exception) {
                val message = e.localizedMessage ?: "Error al guardar la palabra"
                _uiState.update { it.copy(isLoading = false, errorMessage = message) }
                _uiMessage.send(AddWordUiMessage.Info(message))
            }
        }
    }
}
