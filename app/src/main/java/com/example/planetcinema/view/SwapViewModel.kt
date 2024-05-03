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
    /*val uiState: StateFlow<SpinUiState> = filmRepository.getAllFilmsStream().map { SpinUiState(it.random()) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = SpinUiState()
        )
*/
    var uiState by mutableStateOf(SpinUiState())
        private set
    init {
        viewModelScope.launch {
            uiState = SpinUiState(actualFilm =  filmRepository.getAllFilmsStream()
                .first().random())
        }
    }

    fun generateNewFilm() {
        viewModelScope.launch {
            uiState = SpinUiState(
                actualFilm = filmRepository.getAllFilmsStream()
                    .first().random()
            )
        }
    }


    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class SpinUiState(
    val actualFilm : Film = Film(name = "ERROR", id = -1, autor = "ERROR", url = "https://cdn.pixabay.com/photo/2017/02/12/21/29/false-2061132_640.png" , mark = 0.0f,)
)
