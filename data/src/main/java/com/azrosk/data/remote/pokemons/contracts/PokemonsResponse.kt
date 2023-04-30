package com.azrosk.data.remote.pokemons.contracts

import com.google.gson.annotations.SerializedName

data class PokemonsResponse(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
)
