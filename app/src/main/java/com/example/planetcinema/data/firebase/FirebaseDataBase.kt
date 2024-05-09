package com.example.planetcinema.data.firebase

import android.util.Log
import com.example.planetcinema.data.Film
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import kotlinx.coroutines.tasks.await

class FirebaseDataBase {
    private val TAG = "FireBase"
    private lateinit var database: DatabaseReference

    fun initializeDbRef() {
        database = Firebase.database.reference
    }

    fun writeNewFilm(film : Film) {
        database.child("films").child(film.name + "_" +film.autor).setValue(film)
    }

    suspend fun getFilms() : MutableList<Film> {
        val filmList = mutableListOf<Film>()

        try {
            val dataSnapshot = database.child("films").get().await()
            if (dataSnapshot.exists()) {
                dataSnapshot.children.forEach { filmSnapshot ->
                    val film = makeFromDataSnapshotToFilm(filmSnapshot)
                    filmList.add(film)
                }
            } else {
                Log.i(TAG, "No films found")
            }
        } catch (exception: Exception) {
            Log.e(TAG, "Error getting data", exception)
        }

        return filmList
    }


    suspend fun getFilmById(id : String) : Film? {
        var film : Film? = null
        try {
            val filmSnapshot = database.child("films").child(id).get().await()
            if (filmSnapshot.exists()) {
                film = makeFromDataSnapshotToFilm(filmSnapshot)
            } else {
                Log.i(TAG, "No films found")
            }
        } catch (exception: Exception) {
            Log.e(TAG, "Error getting data", exception)
        }
        return film
    }

    private fun makeFromDataSnapshotToFilm(data : DataSnapshot) : Film {
        val name = data.child("name").getValue(String::class.java)
        val author = data.child("autor").getValue(String::class.java)
        val mark = data.child("mark").getValue(Float::class.java)
        val url = data.child("url").getValue(String::class.java)
        return Film(name = name !!, autor = author !!, mark = mark !!, url = url !!)
    }
}