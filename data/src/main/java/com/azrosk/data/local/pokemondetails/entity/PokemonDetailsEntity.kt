package com.azrosk.data.local.pokemondetails.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_details_table")
data class PokemonDetailsEntity(
    @PrimaryKey val name: String = "",
    val imageUrl: String = "",
    val type: String = "",
    val weight: Int = 0,
    val height: Int = 0
)
