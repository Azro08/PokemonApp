package com.azrosk.logic.viewmodel

import androidx.lifecycle.viewModelScope
import com.azrosk.domain.usecase.GetPokemonDetailsUseCase
import com.azrosk.logic.mapper.pokemonDetailsResponseToPokemonDetails
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
    private val useCase: GetPokemonDetailsUseCase
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
                } catch (ex: Exception) {
                    responsePokemonDetails.value = ScreenState.Error(ex.message.toString())
                }
            }
        } catch (ex: UnknownHostException) {//if internet disconnected get data from db
            responsePokemonDetails.value = ScreenState.Loading(null)

        }
    }

}
