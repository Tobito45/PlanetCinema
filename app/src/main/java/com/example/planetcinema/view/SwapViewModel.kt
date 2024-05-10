package com.example.planetcinema.view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planetcinema.data.Film
import com.example.planetcinema.data.FilmsRepository
import com.example.planetcinema.data.firebase.FilmFirebaseRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SwapViewModel(
    private val filmLocalRepository: FilmsRepository,
    private val filmFireRepository: FilmFirebaseRepository
) : ViewModel() {
    var uiState by mutableStateOf(SpinUiState(null))
        private set
    init {
        generateNewFilm()
    }

    fun generateNewFilm() {
        viewModelScope.launch {
            val filmsOfUser = filmLocalRepository.getAllFilmsStream().first()
            val randomFilms = filmFireRepository.getAllFilmWithout(filmsOfUser)
            uiState = SpinUiState(actualFilm = if(randomFilms.isEmpty()) null else randomFilms.random())
        }
    }

    suspend fun checkFilm() {
        val actualFilm = uiState.actualFilm
        if (actualFilm != null) {
            filmLocalRepository.insertFilm(actualFilm.take())
        }
        generateNewFilm()
    }

    fun showBottomSheet() {
        var copy = uiState
        uiState = SpinUiState(
            actualFilm = copy.actualFilm,
        )
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

}

data class SpinUiState(
    val actualFilm : Film?,
)
