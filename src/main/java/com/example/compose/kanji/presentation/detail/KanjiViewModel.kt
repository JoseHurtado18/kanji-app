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
data class KanjiUiState(
    val kanjis: List<Kanji> = emptyList(),
    val filteredKanjis: List<Kanji> = emptyList(),
    val selectedKanji: Kanji? = null,
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val selectedJlptLevel: JlptLevel? = null,
    val errorMessage: String? = null
)

/**
 * Eventos UI (acciones del usuario / intención MVI) para la Feature Kanji.
 */
sealed interface KanjiEvent {
    data class OnSearchQueryChange(val query: String) : KanjiEvent
    data class OnJlptLevelFilterChange(val level: JlptLevel?) : KanjiEvent
    data class LoadKanjiById(val id: Int) : KanjiEvent
    data class SelectKanji(val kanji: Kanji?) : KanjiEvent
    data class AddKanji(val kanji: Kanji) : KanjiEvent
    data class UpdateKanji(val kanji: Kanji) : KanjiEvent
    data class DeleteKanji(val kanji: Kanji) : KanjiEvent
    data class AddExampleWord(val kanjiId: Int, val exampleWord: ExampleWord) : KanjiEvent
    data class DeleteExampleWord(val kanjiId: Int, val exampleWord: ExampleWord) : KanjiEvent
    data object ClearError : KanjiEvent
    data object ClearSelectedKanji : KanjiEvent
}

/**
 * Efectos secundarios únicos (Toasts, navegación, etc.).
 */
sealed interface KanjiUiEffect {
    data class ShowMessage(val message: String) : KanjiUiEffect
    data class KanjiAdded(val kanjiId: Int) : KanjiUiEffect
    data object KanjiUpdated : KanjiUiEffect
    data object KanjiDeleted : KanjiUiEffect
    data object WordAdded : KanjiUiEffect
    data object WordDeleted : KanjiUiEffect
    data object NavigateBack : KanjiUiEffect
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

    private val _uiEffect = Channel<KanjiUiEffect>(Channel.BUFFERED)
    val uiEffect = _uiEffect.receiveAsFlow()

    init {
        loadAllKanjis()
    }

    /**
     * Dispatcher principal de eventos para arquitectura MVI.
     */
    fun onEvent(event: KanjiEvent) {
        when (event) {
            is KanjiEvent.OnSearchQueryChange -> onSearchQueryChange(event.query)
            is KanjiEvent.OnJlptLevelFilterChange -> onJlptFilterChange(event.level)
            is KanjiEvent.LoadKanjiById -> loadKanjiById(event.id)
            is KanjiEvent.SelectKanji -> selectKanji(event.kanji)
            is KanjiEvent.AddKanji -> addKanji(event.kanji)
            is KanjiEvent.UpdateKanji -> updateKanji(event.kanji)
            is KanjiEvent.DeleteKanji -> deleteKanji(event.kanji)
            is KanjiEvent.AddExampleWord -> addExampleWord(event.kanjiId, event.exampleWord)
            is KanjiEvent.DeleteExampleWord -> deleteExampleWord(event.kanjiId, event.exampleWord)
            is KanjiEvent.ClearError -> clearError()
            is KanjiEvent.ClearSelectedKanji -> clearSelectedKanji()
        }
    }

    /**
     * Observa el flujo continuo de kanjis desde la base de datos (Room).
     */
    fun loadAllKanjis() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            kanjiUseCases.getAllKanjis()
                .catch { throwable ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = throwable.localizedMessage ?: "Error al cargar kanjis"
                        )
                    }
                    _uiEffect.send(KanjiUiEffect.ShowMessage(throwable.localizedMessage ?: "Error al cargar kanjis"))
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
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = throwable.localizedMessage ?: "Error al cargar el detalle del kanji"
                        )
                    }
                    _uiEffect.send(KanjiUiEffect.ShowMessage("Error al cargar detalle del kanji"))
                }
                .collect { kanji ->
                    _uiState.update {
                        it.copy(
                            selectedKanji = kanji,
                            isLoading = false
                        )
                    }
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
                _uiEffect.send(KanjiUiEffect.KanjiAdded(newId))
                _uiEffect.send(KanjiUiEffect.ShowMessage("Kanji agregado exitosamente"))
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.localizedMessage ?: "Error al agregar kanji"
                    )
                }
                _uiEffect.send(KanjiUiEffect.ShowMessage(e.localizedMessage ?: "Error al agregar kanji"))
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
                _uiEffect.send(KanjiUiEffect.KanjiUpdated)
                _uiEffect.send(KanjiUiEffect.ShowMessage("Kanji actualizado"))
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.localizedMessage ?: "Error al actualizar kanji"
                    )
                }
                _uiEffect.send(KanjiUiEffect.ShowMessage(e.localizedMessage ?: "Error al actualizar kanji"))
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
                _uiEffect.send(KanjiUiEffect.KanjiDeleted)
                _uiEffect.send(KanjiUiEffect.ShowMessage("Kanji eliminado"))
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.localizedMessage ?: "Error al eliminar kanji"
                    )
                }
                _uiEffect.send(KanjiUiEffect.ShowMessage(e.localizedMessage ?: "Error al eliminar kanji"))
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
                _uiEffect.send(KanjiUiEffect.WordAdded)
                _uiEffect.send(KanjiUiEffect.ShowMessage("Palabra de ejemplo agregada"))
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(errorMessage = e.localizedMessage ?: "Error al agregar palabra de ejemplo")
                }
                _uiEffect.send(KanjiUiEffect.ShowMessage(e.localizedMessage ?: "Error al agregar palabra"))
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
                _uiEffect.send(KanjiUiEffect.WordDeleted)
                _uiEffect.send(KanjiUiEffect.ShowMessage("Palabra de ejemplo eliminada"))
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(errorMessage = e.localizedMessage ?: "Error al eliminar palabra de ejemplo")
                }
                _uiEffect.send(KanjiUiEffect.ShowMessage(e.localizedMessage ?: "Error al eliminar palabra"))
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
* Ejemplo de uso
* @Composable
fun KanjiListScreen(
    viewModel: KanjiViewModel = hiltViewModel(),
    onKanjiClick: (Kanji) -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    // Manejo de efectos (Toasts/Snackbars)
    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is KanjiUiEffect.ShowMessage -> { /* Mostrar Snackbar */ }
                else -> Unit
            }
        }
    }

    // Usar state.filteredKanjis, state.isLoading, etc.
}*/