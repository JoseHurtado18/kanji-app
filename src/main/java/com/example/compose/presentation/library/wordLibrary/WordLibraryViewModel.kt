package com.example.compose.presentation.library.wordLibrary

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
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.contains

data class WordLibraryUiState(
    val words: List<Word> = emptyList(),
    val filteredWords: List<Word> = emptyList(),
    val selectedWord: Word? = null,
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val selectedTypeWord:  TypeWord? = null,
    val selectedFilters: Set<FilterTypeWord> = emptySet(),
    val errorMessage: String? = null
){
    // Se recalcula automáticamente cada vez que accedas a él
    val total_kanjis: Int
        get() = words.size
    val total_words: Int
        get() = words.size
}

sealed interface WordLibraryUiMessage{
    data class Info(val text: String) : WordLibraryUiMessage
}

@HiltViewModel
class WordLibraryViewModel @Inject constructor(
    private val wordUseCases: WordUseCases
) : ViewModel(){

    private val _uiState = MutableStateFlow(WordLibraryUiState())
    val uiState: StateFlow<WordLibraryUiState> = _uiState.asStateFlow()

    private val _uiMessage = Channel<WordLibraryUiMessage>(Channel.BUFFERED)
    val uiMessage = _uiMessage.receiveAsFlow()

    init {
        loadAllWords()
    }


    fun loadAllWords() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            wordUseCases.getAllWordsUseCase()
                .catch { throwable ->
                    val message = throwable.localizedMessage ?: "Error al cargar las palabras"
                    _uiState.update { it.copy(isLoading = false, errorMessage = message) }
                    _uiMessage.send(WordLibraryUiMessage.Info(message))
                }
                .collect { words ->
                    _uiState.update { currentState ->
                        val filtered = applyFilterWord(words, currentState.searchQuery, currentState.selectedFilters)
                        // Si hay una palabra seleccionada actualmente, lo actualizamos con los datos nuevos
                        val updatedSelected = currentState.selectedWord?.let { current ->
                            words.find { it.id == current.id } ?: current
                        }
                        currentState.copy(
                            words = words,
                            filteredWords = filtered,
                            selectedWord = updatedSelected,
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }
        }
    }



    fun onSearchQueryChanged(query: String) {
        _uiState.update { currentState ->
            val filtered = applyFilterWord(currentState.words, query, currentState.selectedFilters)
            currentState.copy(
                searchQuery = query,
                filteredWords = filtered
            )
        }
    }

    fun onFilterChanged(filters: Set<FilterTypeWord>) {
        _uiState.update { currentState ->
            val filtered = applyFilterWord(currentState.words, currentState.searchQuery, filters)
            currentState.copy(
                selectedFilters = filters,
                filteredWords = filtered
            )
        }
    }

    private fun applyFilterWord(
        words: List<Word>,
        query: String,
        filters: Set<FilterTypeWord>
    ): List<Word> {
        val typeFilters = filters.mapNotNull { it.toTypeWord() }.toSet()
        val filterPendientes = filters.contains(FilterTypeWord.PENDIENTES)
        val filterTodos = filters.isEmpty() || filters.contains(FilterTypeWord.TODOS)

        return words.filter { word ->
            // Filtro por type words chips
            val matchesLevel = when {
                filterTodos -> true
                filterPendientes && typeFilters.isNotEmpty() -> word.isDueForReview && word.typeWord in typeFilters
                filterPendientes -> word.isDueForReview
                typeFilters.isNotEmpty() -> word.typeWord in typeFilters
                else -> true
            }

            // Filtro por búsqueda
            val cleanQuery = query.trim()
            val matchesQuery = cleanQuery.isBlank() ||
                    word.kanjiWord.contains(cleanQuery, ignoreCase = true) ||
                    word.meaning.contains(cleanQuery, ignoreCase = true) ||
                    word.reading.contains(cleanQuery, ignoreCase = true)

            matchesLevel && matchesQuery
        }
    }
}