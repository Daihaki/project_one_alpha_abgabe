package com.example.project_one_alpha.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.project_one_alpha.db.datamodel.PokemonDb
import com.example.project_one_alpha.db.datamodel.PokemonMove
import com.example.project_one_alpha.db.datamodel.PokemonMoveCrossRef
import com.example.project_one_alpha.db.datamodel.PokemonType
import com.example.project_one_alpha.db.datamodel.PokemonTypeCrossRef

@Database(
    entities = [
        PokemonDb::class,
        PokemonMove::class,
        PokemonType::class,
        PokemonMoveCrossRef::class,
        PokemonTypeCrossRef::class,
    ],
    version = 2
)

abstract class PokemonDatabase : RoomDatabase() {
    abstract val dao: PokemonDao
}

private lateinit var INSTANCE: PokemonDatabase

fun getDatabase(context: Context): PokemonDatabase {

    synchronized(PokemonDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                PokemonDatabase::class.java,
                "pokemon_database"
            )
                .fallbackToDestructiveMigration().build()
        }
        return INSTANCE
    }
}
