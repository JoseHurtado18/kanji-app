package com.example.compose.kanji.presentation.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose.kanji.domain.model.Kanji
import com.example.compose.kanji.domain.usecase.KanjiUseCases
import com.example.compose.kanji.presentation.add.FormKanjiUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject

sealed interface EditKanjiUiMessage {
    data class Info(val text: String) : EditKanjiUiMessage
    object KanjiUpdated : EditKanjiUiMessage
}

@HiltViewModel
class EditViewModel @Inject constructor(
    private val kanjiUseCases: KanjiUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val kanjiId: Int = checkNotNull(savedStateHandle["kanjiId"])

    private val _uiState = MutableStateFlow(FormKanjiUiState())
    val uiState: StateFlow<FormKanjiUiState> = _uiState.asStateFlow()

    private val _uiMessage = Channel<EditKanjiUiMessage>(Channel.BUFFERED)
    val uiMessage = _uiMessage.receiveAsFlow()

    /** Guardamos el dateAdded original para no perderlo al actualizar. */
    private var originalDateAdded: LocalTime = LocalTime.now()

    init {
        loadKanji()
    }

    private fun loadKanji() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val kanji = kanjiUseCases.getKanji(kanjiId).firstOrNull()
                if (kanji != null) {
                    originalDateAdded = kanji.dateAdded
                    _uiState.value = FormKanjiUiState(
                        caracter = kanji.character,
                        significado = kanji.meaningEs,
                        onyomi = kanji.onYomi,
                        kunyomi = kanji.kunYomi,
                        radical = kanji.radical,
                        trazos = if (kanji.strokeCount > 0) kanji.strokeCount.toString() else "",
                        mnemotecnia = kanji.mnemonic,
                        notas = kanji.notes,
                        selectedJlptLevel = kanji.jlptLevel,
                        exampleWords = kanji.exampleWords,
                        isLoading = false
                    )
                } else {
                    _uiState.update { it.copy(isLoading = false, errorMessage = "Kanji no encontrado") }
                    _uiMessage.send(EditKanjiUiMessage.Info("Kanji no encontrado"))
                }
            } catch (e: Exception) {
                val message = e.localizedMessage ?: "Error al cargar kanji"
                _uiState.update { it.copy(isLoading = false, errorMessage = message) }
                _uiMessage.send(EditKanjiUiMessage.Info(message))
            }
        }
    }

    // --- Actualización de cada campo ---

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

    fun onJlptLevelChange(level: com.example.compose.kanji.domain.model.JlptLevel?) {
        _uiState.update { it.copy(selectedJlptLevel = level) }
    }

    fun addExampleWord() {
        _uiState.update {
            it.copy(
                exampleWords = it.exampleWords +
                        com.example.compose.kanji.domain.model.ExampleWord(word = "", reading = "", meaningEs = "")
            )
        }
    }

    fun onExampleWordChange(index: Int, value: String) {
        _uiState.update { state ->
            val updated = state.exampleWords.toMutableList()
            if (index in updated.indices) {
                updated[index] = updated[index].copy(word = value)
            }
            state.copy(exampleWords = updated)
        }
    }

    fun removeExampleWordAt(index: Int) {
        _uiState.update { state ->
            val updated = state.exampleWords.toMutableList()
            if (index in updated.indices) {
                updated.removeAt(index)
            }
            state.copy(exampleWords = updated)
        }
    }

    /**
     * Construye el Kanji actualizado y lo guarda con updateKanji.
     */
    fun submit() {
        val current = _uiState.value

        if (!current.isValid) {
            _uiState.update { it.copy(errorMessage = "El carácter y el significado son obligatorios") }
            return
        }

        val kanji = Kanji(
            id = kanjiId,
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
            dateAdded = originalDateAdded
        )

        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true, errorMessage = null) }
                kanjiUseCases.updateKanji(kanji)
                _uiState.update { it.copy(isLoading = false) }
                _uiMessage.send(EditKanjiUiMessage.KanjiUpdated)
                _uiMessage.send(EditKanjiUiMessage.Info("Kanji actualizado exitosamente"))
            } catch (e: Exception) {
                val message = e.localizedMessage ?: "Error al actualizar kanji"
                _uiState.update { it.copy(isLoading = false, errorMessage = message) }
                _uiMessage.send(EditKanjiUiMessage.Info(message))
            }
        }
    }
}
