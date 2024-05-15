package com.example.planetcinema.starters

import android.app.Application
import com.example.planetcinema.tools.ModelPreferencesManager
import com.example.planetcinema.data.AppContainer
import com.example.planetcinema.data.AppDataContainer

class PlanetCinemaApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        ModelPreferencesManager.with(this)
        container = AppDataContainer(this)
    }
}