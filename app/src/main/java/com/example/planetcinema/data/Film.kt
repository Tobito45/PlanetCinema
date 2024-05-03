package com.example.planetcinema.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "films")
data class Film (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name : String,
    val autor : String,
    val mark : Float,
    val url : String
)