package com.example.planetcinema.data

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers


interface AppContainer {
    val filmsRepository: FilmsRepository
}


class AppDataContainer(private val context: Context) : AppContainer {

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override val filmsRepository: FilmsRepository by lazy {
        OfflineFilmRepository(FilmDatabase.getDatabase(context, applicationScope).filmDao())
    }
}