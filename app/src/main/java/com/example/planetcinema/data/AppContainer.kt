package com.example.planetcinema.data

import android.content.Context
import com.example.planetcinema.data.firebase.FilmFirebaseRepository
import com.example.planetcinema.data.firebase.OfflineFilmFirebaseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers


interface AppContainer {
    val filmsRepository: FilmsRepository
    val filmsFireRepository: FilmFirebaseRepository
}


class AppDataContainer(private val context: Context) : AppContainer {

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override val filmsRepository: FilmsRepository by lazy {
        OfflineFilmRepository(FilmDatabase.getDatabase(context, applicationScope).filmDao())
    }

    override val filmsFireRepository: FilmFirebaseRepository by lazy {
        OfflineFilmFirebaseRepository()
    }
}