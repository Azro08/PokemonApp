package com.azrosk.mypokemonapp.di

import com.azrosk.domain.repository.local.PokemonDetailsRoomRepository
import com.azrosk.domain.repository.local.PokemonRoomRepository
import com.azrosk.domain.usecase.GetPokemonDetailsUseCase
import com.azrosk.domain.usecase.GetPokemonsUseCase
import com.azrosk.logic.viewmodel.PokemonDetailsViewModel
import com.azrosk.logic.viewmodel.PokemonsListViewModel
import com.azrosk.ui.pokemondetails.PokemonDetailsViewModelApi
import com.azrosk.ui.pokemonlist.PokemonsListViewModelApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PresentationModule {

    @Provides
    @Singleton
    fun providePokemonViewModelApi(
        useCase: GetPokemonsUseCase,
        repository: PokemonRoomRepository
    ): PokemonsListViewModelApi =
        PokemonsListViewModel(useCase, repository)

    @Provides
    @Singleton
    fun providePokemonDetailsViewModelApi(
        useCase: GetPokemonDetailsUseCase,
        repository: PokemonDetailsRoomRepository
    ): PokemonDetailsViewModelApi =
        PokemonDetailsViewModel(useCase, repository)


}
