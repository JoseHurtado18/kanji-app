package com.example.compose.presentation.library.kanjiLibrary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose.domain.model.JlptLevel
import com.example.compose.domain.model.Kanji
import com.example.compose.domain.usecase.KanjiUseCases

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


data class KanjiLibraryUiState(
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

sealed interface KanjiLibraryUiMessage{
    data class Info(val text: String) : KanjiLibraryUiMessage
}

@HiltViewModel
class KanjiLibraryViewModel @Inject constructor(
    private val kanjiUseCases: KanjiUseCases
) : ViewModel(){

    private val _uiState = MutableStateFlow(KanjiLibraryUiState())
    val uiState: StateFlow<KanjiLibraryUiState> = _uiState.asStateFlow()

    private val _uiMessage = Channel<KanjiLibraryUiMessage>(Channel.BUFFERED)
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
                    _uiMessage.send(KanjiLibraryUiMessage.Info(message))
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



    fun onSearchQueryChanged(query: String) {
        _uiState.update { currentState ->
            val filtered = applyFilter(currentState.kanjis, query, currentState.selectedFilters)
            currentState.copy(
                searchQuery = query,
                filteredKanjis = filtered
            )
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
        val jlptFilters = filters.mapNotNull { it.toJlptLevel() }.toSet()
        val filterTodos = filters.isEmpty() || filters.contains(FilterType.TODOS)

        return kanjis.filter { kanji ->
            val matchesLevel = filterTodos || (jlptFilters.isNotEmpty() && kanji.jlptLevel in jlptFilters)

            val cleanQuery = query.trim()
            val matchesQuery = cleanQuery.isBlank() ||
                    kanji.character.contains(cleanQuery, ignoreCase = true) ||
                    kanji.meaningEs.contains(cleanQuery, ignoreCase = true) ||
                    kanji.onYomi.contains(cleanQuery, ignoreCase = true) ||
                    kanji.kunYomi.contains(cleanQuery, ignoreCase = true) ||
                    kanji.exampleWords.any { word ->
                        word.word.contains(cleanQuery, ignoreCase = true) ||
                                word.reading.contains(cleanQuery, ignoreCase = true) ||
                                word.meaningEs.contains(cleanQuery, ignoreCase = true)
                    }

            matchesLevel && matchesQuery
        }
    }
}