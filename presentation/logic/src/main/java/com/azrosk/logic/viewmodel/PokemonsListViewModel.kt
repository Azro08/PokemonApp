package com.azrosk.logic.viewmodel

import androidx.lifecycle.viewModelScope
import com.azrosk.domain.usecase.GetPokemonsUseCase
import com.azrosk.logic.mapper.toPokemonList
import com.azrosk.ui.pokemonlist.PokemonsListViewModelApi
import com.azrosk.ui.pokemonlist.model.PokemonList
import com.azrosk.ui.util.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class PokemonsListViewModel
@Inject constructor(
    private val useCase: GetPokemonsUseCase
) : PokemonsListViewModelApi() {
    override val responsePokemon: MutableStateFlow<ScreenState<PokemonList?>> =
        MutableStateFlow(ScreenState.Loading())

    private val offset = 0
    override fun getPokemons(limit: Int) = viewModelScope.launch {
        try { //if we're connected to internet
            useCase(offset, limit).toPokemonList().let { response ->
                responsePokemon.value = ScreenState.Loading(null)
                try {
                    responsePokemon.value = ScreenState.Success(response)
                } catch (ex: Exception) {
                    responsePokemon.value = ScreenState.Error(ex.message.toString())
                }
            }
        } catch (e: UnknownHostException) { //if we're offline we get the data from db

        }
    }

}
