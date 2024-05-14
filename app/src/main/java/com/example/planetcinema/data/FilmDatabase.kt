package com.example.planetcinema.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope

@Database(entities = [Film::class], version = 2, exportSchema = false)
abstract class FilmDatabase : RoomDatabase() {
    abstract fun filmDao(): FilmDao

    companion object {
        @Volatile
        private var Instance: FilmDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): FilmDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, FilmDatabase::class.java, "item_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }

  /*  private class FilmDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            scope.launch(Dispatchers.IO) {
                val dao = Instance?.filmDao()
                dao?.insert((Film(name ="The Hunger Games",
                    autor =  "Gary Ross",
                    mark =  7.8f,
                    url = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ-X0EK_Uu2NW7mNfSb1Wjw87nmi1Wd7d-BDaHIBfm4sA&s")))
                dao?.insert(Film(name ="The Lord of the Rings",
                    autor =  "Peter Jackson",
                    mark =  8.9f,
                    url = "https://ew.com/thmb/qqofw2-fYfIwaXB2eGSA3xbB7h4=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/image-tout-5741441aeb5f4a46856513f92e182914.jpg"))
                dao?.insert(Film(name ="Scream",
                    autor =  "Wes Craven",
                    mark =  7.4f,
                    url = "https://play-lh.googleusercontent.com/HWDvcxZoBrCz1YLSLy-X_wO0yltscM9Ca1ExOdA8BycTFK7g4_zZVrMFsteTUyhzwzvNE9M9qNeyS4q0ozjw=w240-h480-rw"))
                dao?.insert(Film(name ="Star Wars",
                    autor =  "George Lucas",
                    mark =  6.5f,
                    url = "https://m.media-amazon.com/images/M/MV5BYTRhNjcwNWQtMGJmMi00NmQyLWE2YzItODVmMTdjNWI0ZDA2XkEyXkFqcGdeQXVyNTAyODkwOQ@@._V1_.jpg"))
                dao?.insert(Film(name ="Indiana Jones",
                    autor =  "Steven Spielberg",
                    mark =  8.2f,
                    url = "https://m.media-amazon.com/images/I/71USAqWyT9L._AC_UF894,1000_QL80_.jpg"))
            }
        }
    }*/
}
