package com.example.planetcinema.view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planetcinema.data.Film
import com.example.planetcinema.data.FilmsRepository
import kotlinx.coroutines.launch
import kotlin.random.Random

class WheelViewModel(private val filmRepository: FilmsRepository) : ViewModel()  {
    var uiState by mutableStateOf(WheelUiState())
        private set



    init {
        viewModelScope.launch {
            filmRepository.getCheckedFilmsStream().collect { updatedFilmList ->
                uiState = WheelUiState(
                    films = updatedFilmList,
                )
            }
        }
    }

    suspend fun activeAllButtons(active : Boolean) {
        val filmsInWheel = uiState.filmsInWheel
        filmRepository.getCheckedFilmsStream().collect { updatedFilmList ->
            uiState = WheelUiState(
                films = updatedFilmList,
                filmsInWheel = filmsInWheel,
                activeButtons = active
            )
        }
    }

    fun getRandomWheelNumber(max : Int) : Int {
        return Random.nextInt(0, max)
    }

    suspend fun addFilm(newFilm : Film) {
        val filmsInWheel = uiState.filmsInWheel
        filmRepository.getCheckedFilmsStream().collect { updatedFilmList ->
            uiState = WheelUiState(
                films = updatedFilmList,
                filmsInWheel = filmsInWheel + newFilm,
                activeButtons = true,
                showBottomSheet = true
            )
        }
    }

    suspend fun clearFilms() {
        filmRepository.getCheckedFilmsStream().collect { updatedFilmList ->
            uiState =  WheelUiState(
                films = updatedFilmList,
                filmsInWheel = listOf(),
                activeButtons = true
            )
        }
    }

    fun showBottomSheet(active : Boolean) {
        var copy = uiState
        uiState = WheelUiState(
            films = copy.films,
            filmsInWheel = copy.filmsInWheel,
            activeButtons = copy.activeButtons,
            showBottomSheet = active
        )
    }
}

data class WheelUiState(
    val films : List<Film> = listOf(),
    val filmsInWheel : List<Film> = listOf(),
    var activeButtons: Boolean = true,
    var showBottomSheet : Boolean = false
)
