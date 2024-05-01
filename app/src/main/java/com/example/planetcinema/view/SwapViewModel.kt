package com.example.planetcinema.view

import androidx.lifecycle.ViewModel
import com.example.planetcinema.data.Film
import com.example.planetcinema.data.FilmCreator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SwapViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SpinUiState())
    val uiState: StateFlow<SpinUiState> = _uiState.asStateFlow()

    fun generateNewFilm() {
        _uiState.update { currentState ->
            currentState.copy(
                actualFilm = FilmCreator.GetRandom()
            )
        }
    }
}

data class SpinUiState(
    val actualFilm : Film = FilmCreator.GetRandom()
)
