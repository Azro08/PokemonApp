package com.azrosk.mypokemonapp.di

import android.app.Application
import androidx.room.Room
import com.azrosk.data.local.MyDataBase
import com.azrosk.data.local.pokemondetails.dao.PokemonDetailsDao
import com.azrosk.data.local.pokemons.dao.PokemonsDao
import com.azrosk.data.remote.pokemondetails.PokemonDetailsApi
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

    @Singleton
    @Provides
    fun provideRetrofitPokemonDetailsInstance(retrofit: Retrofit.Builder): PokemonDetailsApi =
        retrofit
            .build()
            .create(PokemonDetailsApi::class.java)

    @Singleton
    @Provides
    fun provideDB(app: Application): MyDataBase =
        Room.databaseBuilder(
            app,
            MyDataBase::class.java,
            MyDataBase.dbName
        ).build()

    @Singleton
    @Provides
    fun providePokemonsDao(db: MyDataBase): PokemonsDao =
        db.getPokemonsDao()

    @Singleton
    @Provides
    fun providePokemonDetailsDao(db: MyDataBase): PokemonDetailsDao =
        db.getPokemonDetailsDao()

}
