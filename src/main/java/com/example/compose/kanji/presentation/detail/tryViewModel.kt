package com.example.compose.kanji.presentation.detail

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
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Estado UI para la Feature Kanji.
 */
data class _KanjiUiState(
    val kanjis: List<Kanji> = emptyList(),
    val filteredKanjis: List<Kanji> = emptyList(),
    val selectedKanji: Kanji? = null,
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val selectedJlptLevel: JlptLevel? = null,
    val errorMessage: String? = null
)

/**
 * Mensajes puntuales para la UI (Snackbars/Toasts).
 * Se mantiene un canal simple para comunicar resultados de operaciones,
 * sin envolverlo en un sistema de "efectos" tipo MVI.
 */
sealed interface KanjiUiMessage {
    data class Info(val text: String) : KanjiUiMessage
    data class KanjiAdded(val kanjiId: Int) : KanjiUiMessage
    data object KanjiUpdated : KanjiUiMessage
    data object KanjiDeleted : KanjiUiMessage
}

/**
 * ViewModel para gestionar el estado y la lógica de la Feature Kanji (MVVM).
 *
 * En MVVM, la Vista llama directamente a los métodos públicos del ViewModel
 * (en lugar de despachar un único `onEvent`), y observa `uiState` para
 * renderizarse de forma reactiva.
 */
@HiltViewModel
class tryViewModel @Inject constructor(
    private val kanjiUseCases: KanjiUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(KanjiUiState(isLoading = true))
    val uiState: StateFlow<KanjiUiState> = _uiState.asStateFlow()

    private val _uiMessage = Channel<KanjiUiMessage>(Channel.BUFFERED)
    val uiMessage = _uiMessage.receiveAsFlow()

    init {
        loadAllKanjis()
    }

    /**
     * Observa el flujo continuo de kanjis desde la base de datos (Room).
     */
    fun loadAllKanjis() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            kanjiUseCases.getAllKanjis()
                .catch { throwable ->
                    val message = throwable.localizedMessage ?: "Error al cargar kanjis"
                    _uiState.update { it.copy(isLoading = false, errorMessage = message) }
                    _uiMessage.send(KanjiUiMessage.Info(message))
                }
                .collect { kanjis ->
                    _uiState.update { currentState ->
                        val filtered = applyFilter(kanjis, currentState.searchQuery, currentState.selectedJlptLevel)
                        // Si hay un kanji seleccionado actualmente, lo actualizamos con los datos nuevos
                        val updatedSelected = currentState.selectedKanji?.let { current ->
                            kanjis.find { it.id == current.id } ?: current
                        }
                        currentState.copy(
                            kanjis = kanjis,
                            filteredKanjis = filtered,
                            selectedKanji = updatedSelected,
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }
        }
    }

    /**
     * Carga o selecciona un kanji específico por su ID.
     */
    fun loadKanjiById(id: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            kanjiUseCases.getKanji(id)
                .catch { throwable ->
                    val message = throwable.localizedMessage ?: "Error al cargar el detalle del kanji"
                    _uiState.update { it.copy(isLoading = false, errorMessage = message) }
                    _uiMessage.send(KanjiUiMessage.Info("Error al cargar detalle del kanji"))
                }
                .collect { kanji ->
                    _uiState.update { it.copy(selectedKanji = kanji, isLoading = false) }
                }
        }
    }

    /**
     * Actualiza el filtro de búsqueda por texto (carácter, significado, on'yomi o kun'yomi).
     */
    fun onSearchQueryChange(query: String) {
        _uiState.update { currentState ->
            currentState.copy(
                searchQuery = query,
                filteredKanjis = applyFilter(currentState.kanjis, query, currentState.selectedJlptLevel)
            )
        }
    }

    /**
     * Filtra la lista por nivel JLPT (N5..N1) o null para mostrar todos.
     */
    fun onJlptFilterChange(level: JlptLevel?) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedJlptLevel = level,
                filteredKanjis = applyFilter(currentState.kanjis, currentState.searchQuery, level)
            )
        }
    }

    /**
     * Asigna un kanji como seleccionado para la vista de detalle.
     */
    fun selectKanji(kanji: Kanji?) {
        _uiState.update { it.copy(selectedKanji = kanji) }
    }

    /**
     * Limpia la selección actual del kanji.
     */
    fun clearSelectedKanji() {
        _uiState.update { it.copy(selectedKanji = null) }
    }

    /**
     * Agrega un nuevo kanji.
     */
    fun addKanji(kanji: Kanji) {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true, errorMessage = null) }
                val newId = kanjiUseCases.addKanji(kanji)
                _uiState.update { it.copy(isLoading = false) }
                _uiMessage.send(KanjiUiMessage.KanjiAdded(newId))
                _uiMessage.send(KanjiUiMessage.Info("Kanji agregado exitosamente"))
            } catch (e: Exception) {
                val message = e.localizedMessage ?: "Error al agregar kanji"
                _uiState.update { it.copy(isLoading = false, errorMessage = message) }
                _uiMessage.send(KanjiUiMessage.Info(message))
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
                _uiMessage.send(KanjiUiMessage.KanjiUpdated)
                _uiMessage.send(KanjiUiMessage.Info("Kanji actualizado"))
            } catch (e: Exception) {
                val message = e.localizedMessage ?: "Error al actualizar kanji"
                _uiState.update { it.copy(isLoading = false, errorMessage = message) }
                _uiMessage.send(KanjiUiMessage.Info(message))
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
                _uiMessage.send(KanjiUiMessage.KanjiDeleted)
                _uiMessage.send(KanjiUiMessage.Info("Kanji eliminado"))
            } catch (e: Exception) {
                val message = e.localizedMessage ?: "Error al eliminar kanji"
                _uiState.update { it.copy(isLoading = false, errorMessage = message) }
                _uiMessage.send(KanjiUiMessage.Info(message))
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
                _uiMessage.send(KanjiUiMessage.Info("Palabra de ejemplo agregada"))
            } catch (e: Exception) {
                val message = e.localizedMessage ?: "Error al agregar palabra de ejemplo"
                _uiState.update { it.copy(errorMessage = message) }
                _uiMessage.send(KanjiUiMessage.Info(message))
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
                _uiMessage.send(KanjiUiMessage.Info("Palabra de ejemplo eliminada"))
            } catch (e: Exception) {
                val message = e.localizedMessage ?: "Error al eliminar palabra de ejemplo"
                _uiState.update { it.copy(errorMessage = message) }
                _uiMessage.send(KanjiUiMessage.Info(message))
            }
        }
    }

    /**
     * Limpia el mensaje de error actual.
     */
    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    /**
     * Filtra la lista de kanjis según texto de búsqueda y nivel JLPT.
     */
    private fun applyFilter(
        kanjis: List<Kanji>,
        query: String,
        jlptLevel: JlptLevel?
    ): List<Kanji> {
        return kanjis.filter { kanji ->
            val matchesLevel = jlptLevel == null || kanji.jlptLevel == jlptLevel
            val matchesQuery = query.isBlank() ||
                    kanji.character.contains(query, ignoreCase = true) ||
                    kanji.meaningEs.contains(query, ignoreCase = true) ||
                    kanji.onYomi.contains(query, ignoreCase = true) ||
                    kanji.kunYomi.contains(query, ignoreCase = true) ||
                    kanji.exampleWords.any { word ->
                        word.word.contains(query, ignoreCase = true) ||
                                word.reading.contains(query, ignoreCase = true) ||
                                word.meaningEs.contains(query, ignoreCase = true)
                    }

            matchesLevel && matchesQuery
        }
    }
}

/*
* Ejemplo de uso en MVVM: la Vista llama directamente a los métodos del ViewModel,
* no despacha un evento a través de un dispatcher único.
*
* @Composable
fun KanjiListScreen(
    viewModel: KanjiViewModel = hiltViewModel(),
    onKanjiClick: (Kanji) -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.uiMessage.collect { message ->
            when (message) {
                is KanjiUiMessage.Info -> { /* Mostrar Snackbar con message.text */ }
                else -> Unit
            }
        }
    }

    // Ejemplo de llamada directa (MVVM):
    // viewModel.onSearchQueryChange(texto)
    // viewModel.selectKanji(kanjiSeleccionado)

    // Usar state.filteredKanjis, state.isLoading, etc.
}
*/