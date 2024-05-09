package com.example.planetcinema

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.planetcinema.view.EditViewModel
import com.example.planetcinema.view.SwapViewModel
import com.example.planetcinema.view.UserListViewModel
import com.example.planetcinema.view.WheelViewModel

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
    }
}

fun CreationExtras.planetCinemaApplication(): PlanetCinemaApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PlanetCinemaApplication)
