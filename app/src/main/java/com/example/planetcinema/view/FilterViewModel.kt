package com.example.planetcinema.view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.planetcinema.data.Film

class FilterViewModel() : ViewModel() {

    var uiState by mutableStateOf(FilterUiState())
        private set


    fun filmsFilter(films : List<Film>) : List<Film> {
        var sortedList = films
        if(uiState.isAbcFilmSorted) {
            sortedList = sortedList.sortedBy { it.name }
        }

        if(uiState.isAbcAutorSorted) {
            sortedList = sortedList.sortedBy { it.autor }
        }

        if(uiState.isAbcAutorSorted && uiState.isAbcFilmSorted) {
            sortedList = sortedList.sortedWith(compareBy<Film> { it.name }.thenBy { it.autor })
        }

        if(uiState.filmContainsWord.isNotEmpty() && uiState.filmContainsWord.isNotBlank()) {
            sortedList = sortedList.filter { it.name.lowercase().contains(uiState.filmContainsWord.lowercase()) }
        }

        if(uiState.autorContainsWord.isNotEmpty() && uiState.autorContainsWord.isNotBlank()) {
            sortedList = sortedList.filter { it.autor.lowercase().contains(uiState.autorContainsWord.lowercase()) }
        }

        sortedList = sortedList.filter { it.mark >= uiState.filmRange.start && it.mark <= uiState.filmRange.endInclusive }
        return sortedList
    }

    fun resetUiState(
        active: Boolean? = null,
        isAbcAutorSorted: Boolean? = null,
        isAbcFilmSorted: Boolean? = null,
        autorContainsWord: String? = null,
        filmContainsWord: String? = null,
        filmRange: ClosedFloatingPointRange<Float>? = null) {
        var copy = uiState
        uiState = FilterUiState(
            showBottomSheet = active ?: copy.showBottomSheet,
            isAbcFilmSorted = isAbcFilmSorted ?: copy.isAbcFilmSorted,
            isAbcAutorSorted = isAbcAutorSorted ?: copy.isAbcAutorSorted,
            filmContainsWord = filmContainsWord ?: copy.filmContainsWord,
            autorContainsWord = autorContainsWord ?: copy.autorContainsWord,
            filmRange = filmRange ?: copy.filmRange
        )
    }

    fun clearUiState() {
        uiState = FilterUiState()
    }
}

data class FilterUiState(
    val showBottomSheet : Boolean = false,
    val isAbcFilmSorted : Boolean = false,
    val isAbcAutorSorted : Boolean = false,
    val filmContainsWord : String = "",
    val autorContainsWord : String = "",
    val filmRange : ClosedFloatingPointRange<Float> = 0.0f..10.0f,
)

