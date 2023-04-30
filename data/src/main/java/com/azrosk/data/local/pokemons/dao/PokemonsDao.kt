package com.azrosk.data.local.pokemons.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.azrosk.data.local.pokemons.entity.PokemonListEntity

@Dao
interface PokemonsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPokemon(pokemonListEntity: PokemonListEntity)

    @Query("DELETE FROM pokemon_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM pokemon_table")
    suspend fun getPokemons(): PokemonListEntity
}
