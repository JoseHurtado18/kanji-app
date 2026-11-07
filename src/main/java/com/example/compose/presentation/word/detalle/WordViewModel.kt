package com.example.compose.presentation.word.detalle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose.domain.model.Kanji
import com.example.compose.domain.model.KanjiReadingAPI
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

data class WordUiState(
    val selectedWord : Word? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean? = false,
    val kanjisWord: List<Kanji> = emptyList(),
    val lecturas: List<KanjiReadingAPI> = emptyList()
)

sealed interface WordDetailUiMessage{
    data class Info(val text: String): WordDetailUiMessage
}

@HiltViewModel
class WordViewModel @Inject constructor(
    private val wordUseCases: WordUseCases
): ViewModel(){

    private val _uiState = MutableStateFlow(WordUiState(isLoading = true))
    val uiState: StateFlow<WordUiState> = _uiState.asStateFlow()

    private val _uiMessage = Channel<WordDetailUiMessage>(Channel.BUFFERED)
    val uiMessage = _uiMessage.receiveAsFlow()

    fun loadWordById(id: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            wordUseCases.getWordUseCase.observe(id)
                .catch { e ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = e.localizedMessage ?: "Error al cargar el detalle de la palabra"
                        )
                    }
                    _uiMessage.send(WordDetailUiMessage.Info("Error al cargar detalle de la palabra"))
                }
                .collect { word ->
                    _uiState.update {
                        it.copy(
                            selectedWord = word,
                            isLoading = false
                        )
                    }
                    getKanjisWord(id)
                    getReadingsForWord(id)
                }

        }
    }

    fun getKanjisWord(id: Int){
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null)}
            wordUseCases.getAllKanjisForWordUseCase(id)
                .catch { e ->
                    _uiState.update { it.copy(isLoading = false, errorMessage = e.localizedMessage ?: "Error al cargar los kanjis") }
                    _uiMessage.send(WordDetailUiMessage.Info("Error al cargar los kanjis"))
                }
                .collect { list ->
                    _uiState.update { it.copy(
                        kanjisWord = list,
                        isLoading = false
                    ) }
                }
        }
    }

    fun getReadingsForWord(id: Int){
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            wordUseCases.observeReadingsForWordUseCase(id)
                .catch { e ->
                    _uiState.update { it.copy(isLoading = false, errorMessage = e.localizedMessage ?: "Error al cargar los kanjis y sus lectura") }
                    _uiMessage.send(WordDetailUiMessage.Info("Error al cargar los kanjis y sus lectura"))
                }
                .collect { list ->
                    _uiState.update { it.copy(lecturas = list, isLoading = false)  }
                }
        }
    }
}
