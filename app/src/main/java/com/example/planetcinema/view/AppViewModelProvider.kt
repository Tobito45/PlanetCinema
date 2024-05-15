package com.example.planetcinema.view

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.planetcinema.starters.PlanetCinemaApplication

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            SwapViewModel(
                planetCinemaApplication().container.filmsRepository,
                planetCinemaApplication().container.filmsFireRepository
            )
        }
        initializer {
            UserListViewModel(
                planetCinemaApplication().container.filmsRepository
            )
        }
        initializer {
            WheelViewModel(
                planetCinemaApplication().container.filmsRepository
            )
        }
        initializer {
            EditViewModel(
                planetCinemaApplication().container.filmsRepository
            )
        }
        initializer {
            FilterViewModel()
        }
    }
}

fun CreationExtras.planetCinemaApplication(): PlanetCinemaApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PlanetCinemaApplication)
