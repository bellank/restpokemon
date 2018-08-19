package com.one.kumaran.restpokemon.extension

fun String.toTitleCase(): String {
    return this[0].toTitleCase() + this.substring(1)
}
