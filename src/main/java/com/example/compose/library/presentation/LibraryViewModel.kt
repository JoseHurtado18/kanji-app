package com.example.compose.library.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose.kanji.domain.model.JlptLevel
import com.example.compose.kanji.domain.model.Kanji
import com.example.compose.kanji.domain.usecase.KanjiUseCases
import com.example.compose.kanji.presentation.add.FormKanjiUiMessage
import com.example.compose.kanji.presentation.add.FormKanjiUiState
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


data class LibraryUiState(
    val kanjis: List<Kanji> = emptyList(),
    val filteredKanjis: List<Kanji> = emptyList(),
    val selectedKanji: Kanji? = null,
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val selectedJlptLevel: JlptLevel? = null,
    val selectedFilters: Set<FilterType> = emptySet(),
    val errorMessage: String? = null
){
    // Se recalcula automáticamente cada vez que accedas a él
    val total_kanjis: Int
        get() = kanjis.size
}

sealed interface LibraryUiMessage{
    data class Info(val text: String) : LibraryUiMessage
}

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val kanjiUseCases: KanjiUseCases
) : ViewModel(){

    private val _uiState = MutableStateFlow(LibraryUiState())
    val uiState: StateFlow<LibraryUiState> = _uiState.asStateFlow()

    private val _uiMessage = Channel<LibraryUiMessage>(Channel.BUFFERED)
    val uiMessage = _uiMessage.receiveAsFlow()

    init {
        loadAllKanjis()
    }


    fun loadAllKanjis() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            kanjiUseCases.getAllKanjis()
                .catch { throwable ->
                    val message = throwable.localizedMessage ?: "Error al cargar kanjis"
                    _uiState.update { it.copy(isLoading = false, errorMessage = message) }
                    _uiMessage.send(LibraryUiMessage.Info(message))
                }
                .collect { kanjis ->
                    _uiState.update { currentState ->
                        val filtered = applyFilter(kanjis, currentState.searchQuery, currentState.selectedFilters)
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



    fun onFilterChanged(filters: Set<FilterType>) {
        _uiState.update { currentState ->
            val filtered = applyFilter(currentState.kanjis, currentState.searchQuery, filters)
            currentState.copy(
                selectedFilters = filters,
                filteredKanjis = filtered
            )
        }
    }

    private fun applyFilter(
        kanjis: List<Kanji>,
        query: String,
        filters: Set<FilterType>
    ): List<Kanji> {
        return kanjis.filter { kanji ->
            // Filtro por JLPT level chips
            val jlptFilters = filters.mapNotNull { it.toJlptLevel() }.toSet()
            val matchesLevel = jlptFilters.isEmpty() ||
                    filters.contains(FilterType.TODOS) ||
                    kanji.jlptLevel in jlptFilters

            // Filtro por búsqueda
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