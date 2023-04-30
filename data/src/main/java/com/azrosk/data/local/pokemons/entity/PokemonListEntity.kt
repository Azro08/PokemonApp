package com.azrosk.data.local.pokemons.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.azrosk.data.local.pokemons.helper.PokemonEntityTypeConverter

@TypeConverters(PokemonEntityTypeConverter::class)
@Entity(tableName = "pokemon_table")
data class PokemonListEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val results: List<PokemonEntity> = listOf()
)
