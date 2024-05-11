package com.example.planetcinema.view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planetcinema.data.Film
import com.example.planetcinema.data.FilmsRepository
import kotlinx.coroutines.launch

class UserListViewModel(private val filmRepository: FilmsRepository) : ViewModel() {

    var uiState by mutableStateOf(UserListUiState())
        private set

    init {
        viewModelScope.launch {
            filmRepository.getCheckedFilmsStream().collect { updatedFilmList ->
                uiState = UserListUiState(
                    filmList = updatedFilmList,
                    watchedFilms = updatedFilmList.map { it.isWatched }
                )
            }
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }


    suspend fun watchFilm(film : Film, sorting : (List<Film>) -> List<Film> = {it}) {
        filmRepository.updateFilm(film.watch(!film.isWatched))
        getAllFilm(sorting)
    }

    suspend fun getAllFilm(sorting : (List<Film>) -> List<Film> = {it}) {
        filmRepository.getCheckedFilmsStream().collect { updatedFilmList ->
            var sortedList = sorting(updatedFilmList)
            uiState = UserListUiState(
                filmList = sortedList ,
                watchedFilms = sortedList.map { it.isWatched },
            )
        }
    }
}

data class UserListUiState(
    val filmList: List<Film> = listOf(),
    val watchedFilms : List<Boolean> = listOf(),
)