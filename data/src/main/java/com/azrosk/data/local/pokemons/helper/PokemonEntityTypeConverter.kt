package com.azrosk.data.local.pokemons.helper

import androidx.room.TypeConverter
import com.azrosk.data.local.pokemons.entity.PokemonEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PokemonEntityTypeConverter {

    @TypeConverter
    fun fromList(pokemonList: List<PokemonEntity>): String {
        val gson = Gson()
        val type = object : TypeToken<List<PokemonEntity>>() {}.type
        return gson.toJson(pokemonList, type)
    }

    @TypeConverter
    fun toList(pokemonListString: String): List<PokemonEntity> {
        val gson = Gson()
        val type = object : TypeToken<List<PokemonEntity>>() {}.type
        return gson.fromJson(pokemonListString, type)
    }
}
