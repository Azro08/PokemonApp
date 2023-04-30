package com.azrosk.data.local.pokemondetails.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.azrosk.data.local.pokemondetails.entity.PokemonDetailsEntity

@Dao
interface PokemonDetailsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPokemonDetails(pokemonDetails: PokemonDetailsEntity)

    @Query("SELECT * FROM pokemon_details_table")
    suspend fun getPokemonDetails(): List<PokemonDetailsEntity>
}
