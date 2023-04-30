package com.azrosk.domain.usecase

import com.azrosk.domain.repository.network.PokemonsRepository
import javax.inject.Inject

class GetPokemonsUseCase
@Inject constructor(private val repository: PokemonsRepository) {
    suspend operator fun invoke(offset: Int, limit: Int) =
        repository.getPokemons(offset, limit)
}

