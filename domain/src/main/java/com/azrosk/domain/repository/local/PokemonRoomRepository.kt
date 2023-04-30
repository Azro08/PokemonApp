package com.azrosk.domain.repository.local

import com.azrosk.data.local.pokemons.dao.PokemonsDao
import com.azrosk.data.local.pokemons.entity.PokemonListEntity
import javax.inject.Inject

class PokemonRoomRepository
@Inject constructor(private val dao: PokemonsDao) {
    suspend fun addPokemon(pokemonListEntity: PokemonListEntity) = dao.addPokemon(pokemonListEntity)

    suspend fun deleteAll() = dao.deleteAll()

    suspend fun getPokemons(): PokemonListEntity = dao.getPokemons()
}
