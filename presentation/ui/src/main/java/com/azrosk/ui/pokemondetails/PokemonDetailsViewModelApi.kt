package com.azrosk.ui.pokemondetails

import androidx.lifecycle.ViewModel
import com.azrosk.ui.pokemondetails.model.PokemonDetails
import com.azrosk.ui.util.ScreenState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow

abstract class PokemonDetailsViewModelApi : ViewModel() {
    abstract val responsePokemonDetails: Flow<ScreenState<PokemonDetails?>>
    abstract fun getPokemonDetails(id: String, name: String): Job
}
