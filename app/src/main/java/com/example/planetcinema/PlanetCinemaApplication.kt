package com.example.planetcinema

import android.app.Application
import com.example.planetcinema.data.AppContainer
import com.example.planetcinema.data.AppDataContainer

class PlanetCinemaApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}