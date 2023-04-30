package com.azrosk.data.remote.pokemondetails.contracts

import com.google.gson.annotations.SerializedName

data class TypeResponse(
    @SerializedName("type")
    val type: TypeXResponse
)
