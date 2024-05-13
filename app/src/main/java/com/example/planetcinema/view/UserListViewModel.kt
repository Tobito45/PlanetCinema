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


    suspend fun watchFilm(watch : Boolean, seletedFilm: Film? = null, sorting : (List<Film>) -> List<Film> = {it}) {
        if(watch) {
            if (uiState.filmMarkValue.isEmpty() || uiState.filmMarkValue.isBlank()) {
                actualizationDialog(active = true, isError = true)
                return
            }
        }
        val select = uiState.seletedFilm ?: seletedFilm
        if(select != null) {
            filmRepository.updateFilm(select.watch(watch,
               if(uiState.filmMarkValue.isNotEmpty()) uiState.filmMarkValue.replace(",",".").toFloat()
                        else select.userMark ?: -1.0f
            ))
        }
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
    fun actualizationDialog(active : Boolean, seletedFilm: Film? = null, mark : String? = null, isError : Boolean = false) {
        val copy = uiState
        uiState = UserListUiState(
            filmList = copy.filmList,
            watchedFilms = copy.watchedFilms,
            dialogShow = active,
            filmMarkValue = mark ?: if(uiState.filmMarkValue.isNotEmpty() && uiState.filmMarkValue.replace(",",".").toFloat() > 10.0) 10.0f.toString() else uiState.filmMarkValue,
            isDialogError = isError,
            seletedFilm = seletedFilm ?: copy.seletedFilm
        )
    }
}

data class UserListUiState(
    val filmList: List<Film> = listOf(),
    val watchedFilms : List<Boolean> = listOf(),
    val dialogShow : Boolean = false,
    val filmMarkValue : String = "",
    val isDialogError : Boolean = false,
    val seletedFilm : Film? = null,
)