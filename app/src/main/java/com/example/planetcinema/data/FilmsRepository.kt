package com.example.planetcinema.data

import kotlinx.coroutines.flow.Flow

interface FilmsRepository {
    fun getAllFilmsStream(): Flow<List<Film>>

    fun getFilmStream(id: Int): Flow<Film?>

    fun getCheckedFilmsStream() : Flow<List<Film>>

    suspend fun insertFilm(film: Film)

    suspend fun deleteFilm(film: Film)

    suspend fun updateFilm(film: Film)
}