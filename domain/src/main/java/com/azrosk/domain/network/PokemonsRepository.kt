package com.azrosk.domain.repository.network

import com.azrosk.data.remote.pokemons.PokemonsApi
import com.azrosk.data.remote.pokemons.contracts.PokemonListResponse
import javax.inject.Inject

class PokemonsRepository
@Inject constructor(private val api: PokemonsApi) {
    suspend fun getPokemons( offset: Int, limit: Int): PokemonListResponse =
        api.getPokemons(offset, limit)
}
