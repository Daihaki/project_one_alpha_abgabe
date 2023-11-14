package com.example.project_one_alpha.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import com.example.project_one_alpha.db.datamodel.Pokemon
import com.example.project_one_alpha.db.datamodel.PokemonDb
import com.example.project_one_alpha.db.datamodel.PokemonMove
import com.example.project_one_alpha.db.datamodel.PokemonMoveCrossRef
import com.example.project_one_alpha.db.datamodel.PokemonType
import com.example.project_one_alpha.db.datamodel.PokemonTypeCrossRef

@Dao
interface PokemonDao {

    @Query("SELECT * FROM pokemonDb")
    fun getAllPokemon(): LiveData<List<Pokemon>>

    @Query("SELECT * FROM pokemonDb WHERE id=:id")
    fun getPokemon(id: Int): LiveData<Pokemon>

    @Query("SELECT * FROM pokemonDb WHERE id=:id")
    fun getPokemonNonLiveData(id: Int): Pokemon

    @Insert(onConflict = IGNORE)
    fun insertPokemon(pokemon: PokemonDb)

    @Query("SELECT EXISTS(SELECT * FROM pokemonDb WHERE id=:id)")
    fun pokemonExists(id: Int): Boolean

    @Insert(onConflict = IGNORE)
    fun insertPokemonType(type: PokemonType)

    @Insert(onConflict = IGNORE)
    fun insertPokemonTypeCrossRef(crossRef: PokemonTypeCrossRef)

    @Insert(onConflict = IGNORE)
    fun insertPokemonMoveCrossRef(crossRef: PokemonMoveCrossRef)

    @Insert(onConflict = IGNORE)
    fun insertMove(pokemonMove: PokemonMove)

    @Insert(onConflict = IGNORE)
    fun insertType(type: PokemonType)
}