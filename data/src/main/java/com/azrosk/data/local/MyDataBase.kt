package com.azrosk.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.azrosk.data.local.pokemondetails.dao.PokemonDetailsDao
import com.azrosk.data.local.pokemondetails.entity.PokemonDetailsEntity
import com.azrosk.data.local.pokemons.dao.PokemonsDao
import com.azrosk.data.local.pokemons.entity.PokemonListEntity
import com.azrosk.data.local.pokemons.helper.PokemonEntityTypeConverter

@TypeConverters(PokemonEntityTypeConverter::class)
@Database(
    entities = [PokemonListEntity::class, PokemonDetailsEntity::class],
    version = 1,
    exportSchema = true
)
abstract class MyDataBase : RoomDatabase() {
    abstract fun getPokemonsDao(): PokemonsDao
    abstract fun getPokemonDetailsDao(): PokemonDetailsDao

    companion object {
        @Volatile
        private var database: MyDataBase? = null

        const val dbName = "pokemonDB"

        @Synchronized
        fun getInstance(context: Context): MyDataBase {
            return if (database == null) {
                database = Room.databaseBuilder(context, MyDataBase::class.java, dbName).build()
                database as MyDataBase
            } else {
                database as MyDataBase
            }
        }
    }

}
