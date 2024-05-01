package com.example.planetcinema.data

object  FilmCreator {
    private val listFilms : MutableList<Film> = mutableListOf();
    val ListFilms: List<Film>
        get() = listFilms.toList()

    fun GetRandom () : Film {
        return listFilms.random()
    }
    init {
        listFilms += (Film("The Hunger Games", "Gary Ross", 7.8f))
        listFilms += (Film("The Lord of the Rings", "Peter Jackson", 8.9f))
        listFilms += (Film("Scream", "Wes Craven", 7.4f))
        listFilms += (Film("Star Wars", "George Lucas", 6.5f))
        listFilms += (Film("Indiana Jones", "Steven Spielberg", 8.2f))
    }

}