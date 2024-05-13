package com.example.planetcinema.data.firebase

import android.util.Log
import com.example.planetcinema.data.Film

class OfflineFilmFirebaseRepository(private val dataBase: FirebaseDataBase) : FilmFirebaseRepository {
    init {
        dataBase.initializeDbRef()
    }

    override suspend fun getAllFilms(): List<Film> {
        return dataBase.getFilms().toList()
    }

    override suspend fun getFilmByNameAndAutor(name: String, autor: String): Film? {
        return dataBase.getFilmById(name + "_" + autor)
    }

    override suspend fun getAllFilmWithout(list: List<Film>): List<Film> {
        val films = dataBase.getFilms()
        Log.i("TESTIC", list.size.toString())
        list.forEach { film ->
            var detectedFilm : Film? = null
            films.forEach fire@{fireFilm ->
                if(fireFilm.name == film.name && fireFilm.autor == film.autor) {
                    detectedFilm = fireFilm
                    return@fire
                }
            }
            Log.i("TESTIC", detectedFilm?.name ?: "kek")
            if(detectedFilm != null)
                films -= detectedFilm !!
        }
        return films
    }

    override fun insertFilm(film: Film) {
        dataBase.writeNewFilm(film)
    }
}