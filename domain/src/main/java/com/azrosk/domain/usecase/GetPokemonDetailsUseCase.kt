package com.azrosk.domain.usecase

import com.azrosk.domain.repository.network.PokemonDetailsRepository
import javax.inject.Inject

class GetPokemonDetailsUseCase
@Inject constructor(private val repository: PokemonDetailsRepository) {
    suspend operator fun invoke(id: String) = repository.getPokemonDetails(id)
}