package com.example.planetcinema.view

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

class WheelViewModel : ViewModel()  {
    private val randomFilmsList : List<String> = listOf("Screem", "Wallken dead", "The hunger name", "Star wars", "Dasha lox 2")
    private val _uiState = MutableStateFlow(WheelUiState())

    val uiState: StateFlow<WheelUiState> = _uiState.asStateFlow()


    fun activeAllButtons(active : Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                activeButtons = active
            )
        }
    }

    fun getRandomWheelNumber(max : Int) : Int {
        return Random.nextInt(0, max)
    }

    fun addFilm(filmsOld : List<String>) {
        _uiState.update { currentState ->
            currentState.copy(
                films = filmsOld + randomFilmsList[Random.nextInt(0, randomFilmsList.size)]
            )
        }
    }

    fun clearFilms() {
        _uiState.update { currentState ->
            currentState.copy(
                films = listOf()
            )
        }
    }
}

data class WheelUiState(
    val films : List<String> = listOf(),
    var activeButtons: Boolean = true
)
