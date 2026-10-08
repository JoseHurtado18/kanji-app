package com.example.compose.kanji.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose.kanji.domain.model.ExampleWord
import com.example.compose.kanji.domain.model.JlptLevel
import com.example.compose.kanji.domain.model.Kanji
import com.example.compose.kanji.domain.usecase.KanjiUseCases
import com.example.compose.kanji.presentation.add.FormKanjiUiMessage
import com.example.compose.library.presentation.LibraryUiMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Estado UI para la Feature Kanji.
 */
data class KanjiUiState(

    val selectedKanji: Kanji? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val strokePaths: List<String> = emptyList()
)

sealed interface KanjiDetailUiMessage{
    data class Info(val text: String) : KanjiDetailUiMessage
    data class KanjiAdded(val kanjiId: Int) : KanjiDetailUiMessage
    data class KanjiUpdated(val text: String): KanjiDetailUiMessage
    data class KanjiDeleted(val text: String): KanjiDetailUiMessage
    data class WordDeleted(val text: String): KanjiDetailUiMessage
    data class WordAdded(val text: String): KanjiDetailUiMessage
}


/**
 * ViewModel para gestionar el estado y la lógica de la Feature Kanji.
 */
@HiltViewModel
class KanjiViewModel @Inject constructor(
    private val kanjiUseCases: KanjiUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(KanjiUiState(isLoading = true))
    val uiState: StateFlow<KanjiUiState> = _uiState.asStateFlow()

    private val _uiMessage = Channel<KanjiDetailUiMessage>(Channel.BUFFERED)
    val uiMessage = _uiMessage.receiveAsFlow()

    init {
    }


    /**
     * Carga o selecciona un kanji específico por su ID.
     */
    fun loadKanjiById(id: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            kanjiUseCases.getKanji(id)
                .catch { throwable ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = throwable.localizedMessage ?: "Error al cargar el detalle del kanji"
                        )
                    }
                    _uiMessage.send(KanjiDetailUiMessage.Info("Error al cargar detalle del kanji"))
                }
                .collect { kanji ->
                    _uiState.update {
                        it.copy(
                            selectedKanji = kanji,
                            isLoading = false
                        )
                    }
                    getStrokesForKanji(kanji?.character ?: "")
                }
        }
    }

    fun getStrokesForKanji(caracter: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            try {
                // Se asume que getStrokesKanji es una 'suspend fun' que devuelve List<String>
                val strokes = kanjiUseCases.getStrokesKanji(caracter)

                _uiState.update {
                    it.copy(
                        strokePaths = strokes,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.localizedMessage ?: "Error al cargar los trazos del kanji"
                    )
                }
                _uiMessage.send(KanjiDetailUiMessage.Info(e.localizedMessage ?: "Error al cargar los trazos del kanji"))
            }
        }
    }

    /**
     * Actualiza la información de un kanji existente.
     */
    fun updateKanji(kanji: Kanji) {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true, errorMessage = null) }
                kanjiUseCases.updateKanji(kanji)
                _uiState.update { it.copy(isLoading = false) }
                _uiMessage.send(KanjiDetailUiMessage.KanjiUpdated(kanji.character))
                _uiMessage.send(KanjiDetailUiMessage.Info("Kanji actualizado"))
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.localizedMessage ?: "Error al actualizar kanji"
                    )
                }
                _uiMessage.send(KanjiDetailUiMessage.Info(e.localizedMessage ?: "Error al actualizar kanji"))
            }
        }
    }

    /**
     * Elimina un kanji.
     */
    fun deleteKanji(kanji: Kanji) {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true, errorMessage = null) }
                kanjiUseCases.deleteKanji(kanji)
                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        selectedKanji = if (currentState.selectedKanji?.id == kanji.id) null else currentState.selectedKanji
                    )
                }
                _uiMessage.send(KanjiDetailUiMessage.KanjiDeleted(kanji.character))
                _uiMessage.send(KanjiDetailUiMessage.Info("Kanji eliminado"))
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.localizedMessage ?: "Error al eliminar kanji"
                    )
                }
                _uiMessage.send(KanjiDetailUiMessage.Info(e.localizedMessage ?: "Error al eliminar kanji"))
            }
        }
    }

    /**
     * Añade una palabra de ejemplo a un kanji.
     */
    fun addExampleWord(kanjiId: Int, exampleWord: ExampleWord) {
        viewModelScope.launch {
            try {
                kanjiUseCases.addExampleWord(kanjiId, exampleWord)
                _uiMessage.send(KanjiDetailUiMessage.WordAdded(kanjiId.toString()))
                _uiMessage.send(KanjiDetailUiMessage.Info("Palabra de ejemplo agregada"))
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(errorMessage = e.localizedMessage ?: "Error al agregar palabra de ejemplo")
                }
                _uiMessage.send(KanjiDetailUiMessage.Info(e.localizedMessage ?: "Error al agregar palabra"))
            }
        }
    }

    /**
     * Elimina una palabra de ejemplo asociada a un kanji.
     */
    fun deleteExampleWord(kanjiId: Int, exampleWord: ExampleWord) {
        viewModelScope.launch {
            try {
                kanjiUseCases.deleteExampleWord(kanjiId, exampleWord)
                _uiMessage.send(KanjiDetailUiMessage.WordDeleted(kanjiId.toString()))
                _uiMessage.send(KanjiDetailUiMessage.Info("Palabra de ejemplo eliminada"))
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(errorMessage = e.localizedMessage ?: "Error al eliminar palabra de ejemplo")
                }
                _uiMessage.send(KanjiDetailUiMessage.Info(e.localizedMessage ?: "Error al eliminar palabra"))
            }
        }
    }

    /**
     * Limpia el mensaje de error actual.
     */
    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }


}


