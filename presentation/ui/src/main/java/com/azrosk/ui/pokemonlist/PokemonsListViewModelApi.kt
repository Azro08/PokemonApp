package com.azrosk.ui.pokemonlist

import androidx.lifecycle.ViewModel
import com.azrosk.ui.pokemonlist.model.PokemonList
import com.azrosk.ui.util.ScreenState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow

abstract class PokemonsListViewModelApi : ViewModel() {
    abstract fun getPokemons(limit: Int): Job

    abstract val responsePokemon: Flow<ScreenState<PokemonList?>>
}
