package com.azrosk.mypokemonapp.di

import com.azrosk.data.local.pokemondetails.dao.PokemonDetailsDao
import com.azrosk.data.local.pokemons.dao.PokemonsDao
import com.azrosk.data.remote.pokemondetails.PokemonDetailsApi
import com.azrosk.data.remote.pokemons.PokemonsApi
import com.azrosk.domain.repository.local.PokemonDetailsRoomRepository
import com.azrosk.domain.repository.local.PokemonRoomRepository
import com.azrosk.domain.repository.network.PokemonDetailsRepository
import com.azrosk.domain.repository.network.PokemonsRepository
import com.azrosk.domain.usecase.GetPokemonDetailsUseCase
import com.azrosk.domain.usecase.GetPokemonsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Singleton
    @Provides
    fun providePokemonsRepository(api: PokemonsApi): PokemonsRepository =
        PokemonsRepository(api)

    @Singleton
    @Provides
    fun providePokemonsUseCase(repository: PokemonsRepository): GetPokemonsUseCase =
        GetPokemonsUseCase(repository)

    @Singleton
    @Provides
    fun providePokemonDetailsRepository(api: PokemonDetailsApi): PokemonDetailsRepository =
        PokemonDetailsRepository(api)

    @Singleton
    @Provides
    fun providePokemonDetailsUseCase(repository: PokemonDetailsRepository): GetPokemonDetailsUseCase =
        GetPokemonDetailsUseCase(repository)

    @Singleton
    @Provides
    fun providePokemonRoomRepository(dao: PokemonsDao) =
        PokemonRoomRepository(dao)

    @Singleton
    @Provides
    fun providePokemonDetailsRoomRepository(dao: PokemonDetailsDao) =
        PokemonDetailsRoomRepository(dao)
}
