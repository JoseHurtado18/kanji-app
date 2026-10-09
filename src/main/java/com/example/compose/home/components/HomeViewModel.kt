package com.example.compose.home.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose.kanji.domain.usecase.KanjiUseCases
import com.example.compose.library.presentation.LibraryUiMessage
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


data class HomeUiState(
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val kanjisLearned : Int? = 0
)

sealed interface HomeUiMessage{
    data class Info(val text: String): HomeUiMessage
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val kanjiUseCases: KanjiUseCases
): ViewModel(){
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _uiMessage = Channel<HomeUiMessage>(Channel.BUFFERED )
    val uiMessage = _uiMessage.receiveAsFlow()


    fun getLearnedKanjis() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            kanjiUseCases.observeKanjiCount()
                .catch { throwable ->
                    val message = throwable.localizedMessage ?: "Error al cargar kanjis"
                    _uiState.update { it.copy(isLoading = false, errorMessage = message) }
                    _uiMessage.send(HomeUiMessage.Info(message))
                }
                .collect { count ->
                    _uiState.update { it.copy(kanjisLearned = count, isLoading = false)
                    }
                }
        }
    }
}