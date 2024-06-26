package com.example.planetcinema.data.film

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "films")
data class Film (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var takeIt : Boolean = false,
    var isWatched : Boolean = false,
    var name : String,
    var autor : String,
    var mark : Float,
    var url : String,
    var userMark: Float = -1.0f,
    val isCreated : Boolean = false
) {
    fun take() : Film {
        takeIt = true
        return this
    }
    fun watch(set : Boolean, mark: Float) : Film {
        isWatched = set
        userMark = mark
        return this
    }

    fun setNewInfo(newName : String, newAutor: String, newUrl : String, newMark : Float, newUserMark: Float,
                   ) : Film {
        name = newName
        autor = newAutor
        url = newUrl

        mark = newMark
        userMark = newUserMark
        isWatched = newUserMark >= 0.0f
        return this
    }
}