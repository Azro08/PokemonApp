package com.azrosk.logic.mapper

import com.azrosk.data.local.pokemondetails.entity.PokemonDetailsEntity
import com.azrosk.data.remote.pokemondetails.contracts.PokemonDetailsResponse
import com.azrosk.ui.pokemondetails.model.PokemonDetails

internal fun PokemonDetails.toPokemonDetailsEntity(): PokemonDetailsEntity = PokemonDetailsEntity(
    name = name,
    imageUrl = imageUrl,
    type = type,
    weight = weight,
    height = height
)

internal fun pokemonDetailsResponseToPokemonDetails(responseDetails: PokemonDetailsResponse): PokemonDetails {
    return PokemonDetails(
        name = responseDetails.name,
        type = responseDetails.typeResponses[0].type.name,
        imageUrl = responseDetails.spritesResponse.frontDefault,
        weight = responseDetails.weight,
        height = responseDetails.height
    )
}

internal fun PokemonDetailsEntity.toPokemonDetails(): PokemonDetails = PokemonDetails(
    name = name,
    imageUrl = imageUrl,
    type = type,
    weight = weight,
    height = height
)
