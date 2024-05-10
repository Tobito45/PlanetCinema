package com.example.planetcinema.view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class FilterViewModel() : ViewModel() {
    var uiState by mutableStateOf(FilterUiState())
        private set

    fun resetUiState(active : Boolean? = null,
                     isAbcAutorSorted: Boolean? = null,
                     isAbcFilmSorted: Boolean? = null,
                     filmRange: ClosedFloatingPointRange<Float>? = null) {
        var copy = uiState
        uiState = FilterUiState(
            showBottomSheet = active ?: copy.showBottomSheet,
            isAbcFilmSorted = isAbcAutorSorted ?: copy.isAbcFilmSorted,
            isAbcAutorSorted = isAbcFilmSorted ?: copy.isAbcAutorSorted,
            filmRange = filmRange ?: copy.filmRange
        )
    }
}

data class FilterUiState(
    val showBottomSheet : Boolean = false,
    val isAbcFilmSorted : Boolean = false,
    val isAbcAutorSorted : Boolean = false,
    val filmRange : ClosedFloatingPointRange<Float> = 0.0f..10.0f,
)

