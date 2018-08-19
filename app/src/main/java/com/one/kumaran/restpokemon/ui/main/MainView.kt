package com.one.kumaran.restpokemon.ui.main

import com.one.kumaran.restpokemon.repository.model.Pokemon
import com.one.kumaran.restpokemon.repository.state.MainState
import com.one.kumaran.restpokemon.ui.View

interface MainView: View {
    fun populateData(mainState: MainState, pokemon: Pokemon)
    fun showLoading(mainState: MainState)
    fun showError(mainState: MainState)
    fun dataLoadCompleted(mainState: MainState)
}