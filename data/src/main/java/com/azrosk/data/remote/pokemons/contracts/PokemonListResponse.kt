package com.azrosk.data.remote.pokemons.contracts

import com.google.gson.annotations.SerializedName

data class PokemonListResponse (
    @SerializedName("results") val results: List<PokemonsResponse> = listOf()
)
