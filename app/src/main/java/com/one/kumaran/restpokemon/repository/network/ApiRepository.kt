package com.one.kumaran.restpokemon.repository.network

import com.one.kumaran.restpokemon.PokeApplication
import com.one.kumaran.restpokemon.repository.model.Pokemon
import com.one.kumaran.restpokemon.repository.model.PokemonDetail
import com.one.kumaran.restpokemon.extension.toTitleCase
import io.reactivex.Observable
import javax.inject.Inject

const val SPEED = "speed"
const val DEFENSE = "defense"
const val ATTACK = "attack"

class ApiRepository {

    init {
        PokeApplication.appComponent.inject(this)
    }

    @Inject
    lateinit var apiInterface: ApiInterface

    fun getAllPokemonByOffset(limit: Int, offset: Int): Observable<Pokemon> {
        return apiInterface.getAllPokemonsByOffset(limit, offset)
                .map { result -> result.results}
                .flatMapIterable { data -> data }
                .flatMap { data -> processDetails(data.getNumber()) }
    }

    private fun processDetails(number: Int): Observable<Pokemon> {
        return apiInterface.getPokemon(number)
                .flatMap { data ->
                        val statMap = processStats(data)
                        Observable.just(Pokemon(data.name.toTitleCase(),
                                data.sprites.front_default,
                                data.types.joinToString(separator = ", ", transform = { it.type.name }),
                                statMap.get(SPEED),
                                statMap.get(DEFENSE),
                                statMap.get(ATTACK),
                                data.abilities.joinToString(separator = ", ", transform = { it.ability.name }))) }
    }

    private fun processStats(detail: PokemonDetail): HashMap<String, String> {
        val hashMap = hashMapOf<String, String>()
        for (stat in detail.stats) {
            hashMap.set(stat.stat.name, stat.baseStat)
        }
        return hashMap
    }
}
