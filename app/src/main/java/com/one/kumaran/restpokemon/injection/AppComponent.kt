package com.one.kumaran.restpokemon.injection

import com.one.kumaran.restpokemon.repository.network.ApiRepository
import com.one.kumaran.restpokemon.ui.main.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RestModule::class])
interface AppComponent {
    fun inject(apiRepository: ApiRepository)
}