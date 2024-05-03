package com.example.planetcinema.data

import kotlinx.coroutines.flow.Flow

class OfflineFilmRepository(private val filmDao: FilmDao) : FilmsRepository  {
    override fun getAllFilmsStream(): Flow<List<Film>> = filmDao.getAllFilms()

    override fun getFilmStream(id: Int): Flow<Film?> = filmDao.getFilm(id)

    override suspend fun insertFilm(film: Film) = filmDao.insert(film)

    override suspend fun deleteFilm(film: Film) = filmDao.delete(film)

    override suspend fun updateFilm(film: Film) = filmDao.update(film)
}