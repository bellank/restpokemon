package com.one.kumaran.restpokemon.ui

interface Presenter<T: View> {
    fun attach(view: T)
    fun detach()
}