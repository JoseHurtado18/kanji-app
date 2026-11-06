package com.example.compose.presentation.word.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose.domain.model.TypeWord
import com.example.compose.domain.model.Word
import com.example.compose.domain.usecase.WordUseCases

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FormWordUiState(
    val kanjiWord: String = "",
    val reading: String = "",
    val meaning: String = "",
    val selectedTypeWord: TypeWord? = null,
    val sentence: String = "",
    val notes: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
) {
    val isValid: Boolean
        get() = kanjiWord.isNotBlank() && meaning.isNotBlank()
}

sealed interface EditWordUiMessage {
    data class Info(val text: String) : EditWordUiMessage
    object WordUpdated : EditWordUiMessage
}

@HiltViewModel
class EditWordViewModel @Inject constructor(
    private val wordUseCases: WordUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val wordId: Int = checkNotNull(savedStateHandle["wordId"])

    private val _uiState = MutableStateFlow(FormWordUiState())
    val uiState: StateFlow<FormWordUiState> = _uiState.asStateFlow()

    private val _uiMessage = Channel<EditWordUiMessage>(Channel.BUFFERED)
    val uiMessage = _uiMessage.receiveAsFlow()

    private var originalIsDueForReview: Boolean = false

    init {
        loadWord()
    }

    private fun loadWord() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val word = wordUseCases.getWordUseCase(wordId)
                if (word != null) {
                    originalIsDueForReview = word.isDueForReview
                    _uiState.value = FormWordUiState(
                        kanjiWord = word.kanjiWord,
                        reading = word.reading,
                        meaning = word.meaning,
                        selectedTypeWord = word.typeWord,
                        sentence = word.sentence ?: "",
                        notes = word.notes ?: "",
                        isLoading = false
                    )
                } else {
                    _uiState.update { it.copy(isLoading = false, errorMessage = "Palabra no encontrada") }
                    _uiMessage.send(EditWordUiMessage.Info("Palabra no encontrada"))
                }
            } catch (e: Exception) {
                val message = e.localizedMessage ?: "Error al cargar palabra"
                _uiState.update { it.copy(isLoading = false, errorMessage = message) }
                _uiMessage.send(EditWordUiMessage.Info(message))
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

    fun submit() {
        val current = _uiState.value

        if (!current.isValid) {
            _uiState.update { it.copy(errorMessage = "La palabra y el significado son obligatorios") }
            return
        }

        val word = Word(
            id = wordId,
            kanjiWord = current.kanjiWord.trim(),
            reading = current.reading.trim(),
            meaning = current.meaning.trim(),
            typeWord = current.selectedTypeWord,
            sentence = current.sentence.ifBlank { null },
            notes = current.notes.ifBlank { null },
            isDueForReview = originalIsDueForReview
        )

        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true, errorMessage = null) }
                wordUseCases.updateWordUseCase(word)
                _uiState.update { it.copy(isLoading = false) }
                _uiMessage.send(EditWordUiMessage.WordUpdated)
                _uiMessage.send(EditWordUiMessage.Info("Palabra actualizada exitosamente"))
            } catch (e: Exception) {
                val message = e.localizedMessage ?: "Error al actualizar palabra"
                _uiState.update { it.copy(isLoading = false, errorMessage = message) }
                _uiMessage.send(EditWordUiMessage.Info(message))
            }
        }
    }
}
