package com.azrosk.domain.repository.network

import com.azrosk.data.remote.pokemondetails.PokemonDetailsApi
import com.azrosk.data.remote.pokemondetails.contracts.PokemonDetailsResponse
import javax.inject.Inject

class PokemonDetailsRepository
@Inject constructor(private val api: PokemonDetailsApi) {
    suspend fun getPokemonDetails(id: String): PokemonDetailsResponse =
        api.getPokemonDetails(id)
}
