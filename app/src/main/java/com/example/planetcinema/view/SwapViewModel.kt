package com.example.planetcinema.view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planetcinema.data.Film
import com.example.planetcinema.data.FilmsRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SwapViewModel(
    private val filmRepository: FilmsRepository
) : ViewModel() {
    var uiState by mutableStateOf(SpinUiState(null))
        private set
    init {
        generateNewFilm()
    }

    fun generateNewFilm() {
        viewModelScope.launch {
            val randomFilms = filmRepository.getNoneCheckedFilmsStream().first();
            uiState = SpinUiState(actualFilm = if(randomFilms.isEmpty()) null else randomFilms.random())
        }
    }

    suspend fun checkFilm() {
        val actualFilm = uiState.actualFilm
        if (actualFilm != null) {
            filmRepository.updateFilm(actualFilm.take())
        }
        generateNewFilm()
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class SpinUiState(
    val actualFilm : Film?
)
