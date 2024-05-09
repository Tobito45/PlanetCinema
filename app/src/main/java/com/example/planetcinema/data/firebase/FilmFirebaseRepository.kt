package com.example.planetcinema.data.firebase

import com.example.planetcinema.data.Film

interface FilmFirebaseRepository {
    suspend fun getAllFilms() : List<Film>
    suspend fun getFilmByNameAndAutor(name : String, autor : String) : Film?
    suspend fun getAllFilmWithout(list : List<Film>) : List<Film>
    fun insertFilm(film : Film)
}