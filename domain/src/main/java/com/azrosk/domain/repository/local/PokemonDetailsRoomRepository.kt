package com.azrosk.domain.repository.local

import com.azrosk.data.local.pokemondetails.dao.PokemonDetailsDao
import com.azrosk.data.local.pokemondetails.entity.PokemonDetailsEntity
import javax.inject.Inject

class PokemonDetailsRoomRepository
@Inject constructor(private val dao: PokemonDetailsDao) {

    suspend fun addPokemonDetails(pokemonDetails: PokemonDetailsEntity) =
        dao.addPokemonDetails(pokemonDetails)

    suspend fun getPokemonDetails(): List<PokemonDetailsEntity> = dao.getPokemonDetails()
}
