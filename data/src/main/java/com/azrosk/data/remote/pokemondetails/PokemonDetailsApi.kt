package com.azrosk.data.remote.pokemondetails

import com.azrosk.data.remote.pokemondetails.contracts.PokemonDetailsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonDetailsApi {
    @GET("pokemon/{id}")
    suspend fun getPokemonDetails (@Path("id") id : String) : PokemonDetailsResponse
}
