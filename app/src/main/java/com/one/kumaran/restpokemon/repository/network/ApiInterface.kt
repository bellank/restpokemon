package com.one.kumaran.restpokemon.repository.network

import com.one.kumaran.restpokemon.repository.model.PokemonDetail
import com.one.kumaran.restpokemon.repository.model.PokemonMeta
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val ID = "id"

interface ApiInterface {

    @GET ("pokemon")
    fun getAllPokemonsByOffset(@Query("limit") limit: Int, @Query("offset") offset: Int): Observable<PokemonMeta>

    @GET ("pokemon/{id}")
    fun getPokemon(@Path(ID) id: Int): Observable<PokemonDetail>

}
