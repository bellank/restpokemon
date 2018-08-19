package com.one.kumaran.restpokemon.repository.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pokemon(val name: String, val url: String, val types: String, val speed: String?,
                   val defense: String?, val attack: String?, val abilities: String): Parcelable

data class PokemonMeta(val count: Int, val previous: String, val results: List<PokemonResult>,
                       val next: String)

data class PokemonResult(val name: String, val url: String) {
    fun getNumber(): Int {
        val pathArray = url.split("/")
        return Integer.parseInt(pathArray.get(pathArray.size - 2))
    }
}

data class PokemonDetail(val name: String, val sprites: Sprite, val types: List<Types>,
                         val stats: List<Stats>, val abilities: List<Abilities>)

data class Abilities(val ability: Ability)

data class Ability(val name: String)

data class Stats(val stat: Stat, @SerializedName("base_stat") val baseStat: String)

data class Stat(val name: String)

data class Sprite(val front_default: String)

data class Types(val type: Type)

data class Type(val name: String)
