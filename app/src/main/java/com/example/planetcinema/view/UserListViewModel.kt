package com.example.planetcinema.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.planetcinema.data.Film
import com.example.planetcinema.data.FilmsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class UserListViewModel(private val filmRepository: FilmsRepository) : ViewModel() {

    val homeUiState: StateFlow<UserListUiState> =
        filmRepository.getAllFilmsStream().map { UserListUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = UserListUiState()
            )
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class UserListUiState(val itemList: List<Film> = listOf())