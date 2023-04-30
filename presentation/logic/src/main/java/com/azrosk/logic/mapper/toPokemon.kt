package com.azrosk.logic.mapper

import com.azrosk.data.remote.pokemons.contracts.PokemonListResponse
import com.azrosk.data.remote.pokemons.contracts.PokemonsResponse
import com.azrosk.ui.pokemonlist.model.Pokemon
import com.azrosk.ui.pokemonlist.model.PokemonList

internal fun PokemonsResponse.toPokemon(): Pokemon = Pokemon(
    name = name,
    url = url,
)

internal fun PokemonListResponse.toPokemonList(): PokemonList = PokemonList(
    results = results.map {
        it.toPokemon()
    }
)