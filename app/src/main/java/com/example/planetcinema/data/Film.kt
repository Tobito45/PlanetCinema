package com.example.planetcinema.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "films")
data class Film (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var takeIt : Boolean = false,
    var isWatched : Boolean = false,
    val name : String,
    val autor : String,
    val mark : Float,
    val url : String
) {
    fun take() : Film {
        takeIt = true
        return this
    }
    fun watch(set : Boolean) : Film {
        isWatched = set
        return this
    }
}