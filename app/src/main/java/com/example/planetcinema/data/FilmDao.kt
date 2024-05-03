package com.example.planetcinema.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface FilmDao {

    @Query("SELECT * from films")
    fun getAllFilms(): Flow<List<Film>>

    @Query("SELECT * from films WHERE id = :id")
    fun getFilm(id: Int): Flow<Film>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Film)

    @Update
    suspend fun update(item: Film)

    @Delete
    suspend fun delete(item: Film)
}