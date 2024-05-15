package com.example.planetcinema.view

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planetcinema.ModelPreferencesManager
import com.example.planetcinema.data.Film
import com.example.planetcinema.data.FilmsRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlin.random.Random


class WheelViewModel(private val filmRepository: FilmsRepository) : ViewModel()  {
    var uiState by mutableStateOf(WheelUiState())
        private set

    var uiAnimationState by mutableStateOf(WheelAnimationUiState())
        private set

    init {
        val bag = ModelPreferencesManager.get<BagFilms>("KEY_BAG")
        viewModelScope.launch {
            filmRepository.getCheckedFilmsStream().collect { updatedFilmList ->
                uiState = WheelUiState(
                    films = updatedFilmList,
                    filmsInWheel = bag?.films ?: listOf(),
                )
            }
        }
    }

    fun activeAllButtons(active : Boolean) {
        val filmsInWheel = uiState.filmsInWheel
        val films = uiState.films
        uiState = WheelUiState(
            films = films,
            filmsInWheel = filmsInWheel,
            activeButtons = active,
        )
    }

    fun getRandomWheelNumber(max : Int) : Int {
        return Random.nextInt(0, max)
    }

    fun addFilm(newFilm : Film) {
        val films = uiState.films
        val filmsInWheel = uiState.filmsInWheel
        uiState = WheelUiState(
            films = films,
            filmsInWheel = if (containFilm(newFilm)) filmsInWheel - newFilm else filmsInWheel + newFilm,
            activeButtons = true,
            showBottomSheet = true,
        )
    }

    fun containFilm(film: Film) : Boolean = uiState.filmsInWheel.contains(film)

     fun clearFilms() {
         val films = uiState.films
         uiState = WheelUiState(
             films = films,
             filmsInWheel = listOf(),
             activeButtons = true,
         )
     }

    suspend fun reloadState(activeList : Boolean = false, sorting : (List<Film>) -> List<Film> = {it}) {
        val updateFilms = filmRepository.getCheckedFilmsStream().first()
        val sortedFilms = sorting(updateFilms)
        val copy = uiState
        uiState = WheelUiState(
            films = sortedFilms,
            filmsInWheel = copy.filmsInWheel,
            activeButtons = copy.activeButtons,
            showBottomSheet = activeList,
        )

        if(!activeList)
            saveData()
    }

    fun saveData() {
        val bag = BagFilms(uiState.filmsInWheel)
        ModelPreferencesManager.put(bag, "KEY_BAG")
    }
}

data class WheelUiState(
    val films : List<Film> = listOf(),
    val filmsInWheel : List<Film> = listOf(),
    var activeButtons: Boolean = true,
    var showBottomSheet : Boolean = false,
)

data class WheelAnimationUiState(
    val rotation : Animatable<Float, AnimationVector1D> = Animatable(0f),
)
data class BagFilms(var films: List<Film>)
