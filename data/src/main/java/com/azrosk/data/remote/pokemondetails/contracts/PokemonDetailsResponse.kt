package com.azrosk.data.remote.pokemondetails.contracts


import com.google.gson.annotations.SerializedName

data class PokemonDetailsResponse(
    @SerializedName("name") val name: String,
    @SerializedName("sprites") val spritesResponse: SpritesResponse,
    @SerializedName("types") val typeResponses: List<TypeResponse>,
    @SerializedName("weight") val weight: Int,
    @SerializedName("height") val height: Int
)
