package com.example.planetcinema.view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.planetcinema.data.film.Film
import com.example.planetcinema.data.film.FilmsRepository

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
                 inputUserMark = if(gettedFilm == null || gettedFilm.userMark == -1.0f) "" else gettedFilm.userMark.toString(),
             )
         }
    }
    fun editDataFilm(newName: String? = null, newAutor: String? = null, newMark : String? = null,
                     newUrl : String? = null,  newUserMark : String? = null, isWatched: Boolean? = null,
                     isError: Boolean = false) {
        var copy = uiState
        uiState = EditUiState(
            film = uiState.film,
            inputName = newName ?: uiState.inputName,
            inputAutor = newAutor ?: uiState.inputAutor,
            inputMark = newMark ?: uiState.inputMark,
            inputUserMark = newUserMark ?: uiState.inputUserMark,
            inputUrl = newUrl ?: uiState.inputUrl,
            isAdding = uiState.isAdding,
            isError = if(copy.isError) true else isError,
            isWatched = isWatched ?: uiState.isWatched
        )
    }

    suspend fun updateFilm() : Boolean {
        val film = uiState.film
        if(!verificate()) {
            editDataFilm(isError = true)
            return false
        }

        if(film != null) {
            filmRepository.updateFilm(film.setNewInfo(
                newName = uiState.inputName,
                newAutor = uiState.inputAutor,
                newMark = "%.1f".format(uiState.inputMark.replace(",", ".").toFloatOrNull() ?: 0f ).replace(",", ".").toFloat(),
                newUrl = uiState.inputUrl,
                newUserMark = if(uiState.isWatched) uiState.inputUserMark.replace(",",".").toFloat() else -1.0f
                ))
        } else {
            filmRepository.insertFilm(
                Film(
                name = uiState.inputName,
                autor = uiState.inputAutor,
                mark = "%.1f".format(uiState.inputMark.replace(",", ".").toFloatOrNull() ?: 0f ).replace(",", ".").toFloat(),
                url = uiState.inputUrl,
                takeIt = true,
                isCreated = true,
                isWatched = uiState.isWatched,
                userMark = if(uiState.isWatched) uiState.inputUserMark.replace(",",".").toFloat() else -1.0f
            )
            )
        }
        return true
    }

    fun verificate() : Boolean {
        return uiState.inputName.isNotEmpty() && uiState.inputMark.isNotEmpty()
                && uiState.inputAutor.isNotEmpty() && uiState.inputUrl.isNotEmpty() &&
                if(uiState.isWatched) uiState.inputUserMark.isNotEmpty() else true
    }
}

    data class EditUiState(
        val film: Film? = null,
        val inputName : String = "",
        val inputAutor: String = "",
        val inputMark: String = "",
        val inputUrl : String = "",
        val isAdding : Boolean = false,
        val isError : Boolean = false,
        val isWatched : Boolean = false,
        val inputUserMark : String = ""
    )