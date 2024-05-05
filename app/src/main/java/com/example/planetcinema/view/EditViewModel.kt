package com.example.planetcinema.view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.planetcinema.data.Film
import com.example.planetcinema.data.FilmsRepository

class EditViewModel(private val filmRepository: FilmsRepository) : ViewModel() {
    var uiState by mutableStateOf(EditUiState())
        private set

     suspend fun setBasicParameters(filmId: Int) {
         if(filmId == uiState.film?.id || uiState.isAdding) {
            return
         }

         if(filmId == -1) {
             uiState = EditUiState(
                 isAdding = true
             )
             return
         }

         filmRepository.getFilmStream(filmId).collect { gettedFilm ->
             uiState = EditUiState(
                 film = gettedFilm,
                 inputName = (gettedFilm?.name ?: ""),
                 inputAutor = gettedFilm?.autor ?: "",
                 inputMark = gettedFilm?.mark.toString(),
                 inputUrl = gettedFilm?.url ?: "",
             )
         }
    }
    fun editDataFilm(newName: String? = null, newAutor: String? = null, newMark : String? = null, newUrl : String? = null) {
        var copy = uiState
        uiState = EditUiState(
            film = uiState.film,
            inputName = newName ?: uiState.inputName,
            inputAutor = newAutor ?: uiState.inputAutor,
            inputMark = newMark ?: uiState.inputMark,
            inputUrl = newUrl ?: uiState.inputUrl,
            isAdding = uiState.isAdding
        )
    }

    suspend fun updateFilm() {
        val film = uiState.film
        if(film != null) {
            filmRepository.updateFilm(film.setNewInfo(
                newName = uiState.inputName,
                newAutor = uiState.inputAutor,
                newMark = "%.1f".format(uiState.inputMark.replace(",", ".").toFloatOrNull() ?: 0f ).replace(",", ".").toFloat(),
                newUrl = uiState.inputUrl))
        } else {
            filmRepository.insertFilm(Film(
                name = uiState.inputName,
                autor = uiState.inputAutor,
                mark = "%.1f".format(uiState.inputMark.replace(",", ".").toFloatOrNull() ?: 0f ).replace(",", ".").toFloat(),
                url = uiState.inputUrl,
                takeIt = true
            )
            )
        }
    }
}

    data class EditUiState(
        val film: Film? = null,
        val inputName : String = "",
        val inputAutor: String = "",
        val inputMark: String = "",
        val inputUrl : String = "",
        val isAdding : Boolean = false
    )