package com.azrosk.logic.viewmodel

import androidx.lifecycle.viewModelScope
import com.azrosk.domain.repository.local.PokemonDetailsRoomRepository
import com.azrosk.domain.usecase.GetPokemonDetailsUseCase
import com.azrosk.logic.mapper.pokemonDetailsResponseToPokemonDetails
import com.azrosk.logic.mapper.toPokemonDetails
import com.azrosk.logic.mapper.toPokemonDetailsEntity
import com.azrosk.ui.pokemondetails.PokemonDetailsViewModelApi
import com.azrosk.ui.pokemondetails.model.PokemonDetails
import com.azrosk.ui.util.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel
@Inject constructor(
    private val useCase: GetPokemonDetailsUseCase,
    private val repository: PokemonDetailsRoomRepository
) : PokemonDetailsViewModelApi() {
    override val responsePokemonDetails: MutableStateFlow<ScreenState<PokemonDetails?>> =
        MutableStateFlow(ScreenState.Loading())

    override fun getPokemonDetails(id: String, name: String) = viewModelScope.launch {
        try {
            useCase(id).let { response ->
                responsePokemonDetails.value = ScreenState.Loading(null)
                try {
                    val pokemonDetails = pokemonDetailsResponseToPokemonDetails(response)
                    responsePokemonDetails.value = ScreenState.Success(pokemonDetails)
                    repository.addPokemonDetails(pokemonDetails.toPokemonDetailsEntity())
                } catch (ex: Exception) {
                    responsePokemonDetails.value = ScreenState.Error(ex.message.toString())
                }
            }
        } catch (ex: UnknownHostException) {//if internet disconnected get data from db
            responsePokemonDetails.value = ScreenState.Loading(null)
            repository.getPokemonDetails().let {
                try {
                    var isFound = false
                    for (currentPokemon in it) {
                        if (currentPokemon.name == name) {
                            responsePokemonDetails.value =
                                ScreenState.Success(currentPokemon.toPokemonDetails())
                            isFound = true
                        }
                    }
                    if (!isFound) responsePokemonDetails.value =
                        ScreenState.Error("Error loading data, check your connection!")
                } catch (ex: Exception) {
                    responsePokemonDetails.value =
                        ScreenState.Error("Error loading data, check your connection!")
                }
            }
        }
    }

}
