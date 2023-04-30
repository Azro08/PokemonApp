package com.azrosk.mypokemonapp.di

import com.azrosk.data.remote.pokemons.PokemonsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideBaseUrl() = "https://pokeapi.co/api/v2/"

    @Singleton
    @Provides
    fun provideRetrofit(BASE_URL: String): Retrofit.Builder =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())

    @Singleton
    @Provides
    fun provideRetrofitPokemonsInstance(retrofit: Retrofit.Builder): PokemonsApi =
        retrofit
            .build()
            .create(PokemonsApi::class.java)

}
