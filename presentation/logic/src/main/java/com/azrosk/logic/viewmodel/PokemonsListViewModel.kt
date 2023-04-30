package com.azrosk.logic.viewmodel

import androidx.lifecycle.viewModelScope
import com.azrosk.domain.repository.local.PokemonRoomRepository
import com.azrosk.domain.usecase.GetPokemonsUseCase
import com.azrosk.logic.mapper.toPokemonEntityList
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
    private val useCase: GetPokemonsUseCase,
    private val repository: PokemonRoomRepository
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
                    repository.deleteAll()
                    useCase(offset, limit).toPokemonEntityList().let { roomResponse ->
                        if (offset == 0) {
                            repository.deleteAll()
                        }
                        repository.addPokemon(roomResponse)
                    }
                } catch (ex: Exception) {
                    responsePokemon.value = ScreenState.Error(ex.message.toString())
                }
            }
        } catch (e: UnknownHostException) { //if we're offline we get the data from db
            repository.getPokemons().let {
                responsePokemon.value = ScreenState.Loading(null)
                try {
                    if (it.results.isNotEmpty()) {
                        responsePokemon.value = ScreenState.Success(it.toPokemonList())
                    } else {
                        responsePokemon.value = ScreenState.Error("No data found")
                    }
                } catch (ex: Exception) {
                    responsePokemon.value =
                        ScreenState.Error("Error loading data, check your connection!")
                }
            }
        }
    }

}
