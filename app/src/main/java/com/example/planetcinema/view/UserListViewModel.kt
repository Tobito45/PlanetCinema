package com.example.planetcinema.view

import androidx.lifecycle.ViewModel
import com.example.planetcinema.data.Film
import com.example.planetcinema.data.FilmCreator

class UserListViewModel : ViewModel() {

    fun getAllFilms() : List<Film> {
        return FilmCreator.ListFilms;
    }
}