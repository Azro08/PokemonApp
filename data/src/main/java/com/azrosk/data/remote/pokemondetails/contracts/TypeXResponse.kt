package com.azrosk.data.remote.pokemondetails.contracts

import com.google.gson.annotations.SerializedName

data class TypeXResponse(
    @SerializedName("name")
    val name: String,
)
