package com.azrosk.data.remote.pokemons

import com.azrosk.data.remote.pokemons.contracts.PokemonListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonsApi {
    @GET("pokemon")
    suspend fun getPokemons(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): PokemonListResponse
}
