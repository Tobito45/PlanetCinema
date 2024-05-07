package com.example.planetcinema.data

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
    val isCreated : Boolean = false
) {
    fun take() : Film {
        takeIt = true
        return this
    }
    fun watch(set : Boolean) : Film {
        isWatched = set
        return this
    }

    fun setNewInfo(newName : String, newAutor: String, newUrl : String, newMark : Float) : Film {
        name = newName
        autor = newAutor
        url = newUrl

        mark = newMark
        return this
    }
}