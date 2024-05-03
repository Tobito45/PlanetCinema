package com.example.planetcinema.data

object  FilmCreator {
    private val listFilms : MutableList<Film> = mutableListOf();
    val ListFilms: List<Film>
        get() = listFilms.toList()

    fun GetRandom () : Film {
        return listFilms.random()
    }
  /*  init {
        listFilms += (Film("The Hunger Games", "Gary Ross", 7.8f,
                        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ-X0EK_Uu2NW7mNfSb1Wjw87nmi1Wd7d-BDaHIBfm4sA&s"))
        listFilms += (Film("The Lord of the Rings", "Peter Jackson", 8.9f,
                        "https://ew.com/thmb/qqofw2-fYfIwaXB2eGSA3xbB7h4=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/image-tout-5741441aeb5f4a46856513f92e182914.jpg"))
        listFilms += (Film("Scream", "Wes Craven", 7.4f,
                        "https://play-lh.googleusercontent.com/HWDvcxZoBrCz1YLSLy-X_wO0yltscM9Ca1ExOdA8BycTFK7g4_zZVrMFsteTUyhzwzvNE9M9qNeyS4q0ozjw=w240-h480-rw"))
        listFilms += (Film("Star Wars", "George Lucas", 6.5f,
                        "https://m.media-amazon.com/images/M/MV5BYTRhNjcwNWQtMGJmMi00NmQyLWE2YzItODVmMTdjNWI0ZDA2XkEyXkFqcGdeQXVyNTAyODkwOQ@@._V1_.jpg"))
        listFilms += (Film("Indiana Jones", "Steven Spielberg", 8.2f,
                            "https://m.media-amazon.com/images/I/71USAqWyT9L._AC_UF894,1000_QL80_.jpg"))
    }*/

}