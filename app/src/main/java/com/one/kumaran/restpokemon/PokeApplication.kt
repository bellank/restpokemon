package com.one.kumaran.restpokemon

import android.app.Application
import com.one.kumaran.restpokemon.injection.AppComponent
import com.one.kumaran.restpokemon.injection.DaggerAppComponent
import com.one.kumaran.restpokemon.injection.RestModule
import timber.log.Timber

const val baseUrl = "https://pokeapi.co/api/v2/"

class PokeApplication : Application() {

    companion object {
        lateinit var app: PokeApplication
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        plantTimber()
        initDagger()
    }

    private fun plantTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initDagger() {
        appComponent = DaggerAppComponent.builder()
                .restModule(RestModule(baseUrl, app))
                .build()
    }
}