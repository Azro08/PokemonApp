package com.azrosk.data.remote.pokemondetails.contracts

import com.google.gson.annotations.SerializedName

data class SpritesResponse(
    @SerializedName("front_default") val frontDefault: String,
)
