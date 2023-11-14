package com.example.project_one_alpha

import androidx.lifecycle.LiveData
import com.example.project_one_alpha.api.ApiService
import com.example.project_one_alpha.api.datamodel.PokemonInResponse
import com.example.project_one_alpha.db.datamodel.PokemonDb
import com.example.project_one_alpha.db.datamodel.PokemonTypeCrossRef
import com.example.project_one_alpha.db.PokemonDatabase
import com.example.project_one_alpha.db.datamodel.MoveType
import com.example.project_one_alpha.db.datamodel.Pokemon
import com.example.project_one_alpha.db.datamodel.PokemonMoveCrossRef

class Repository(val apiService: ApiService, val db: PokemonDatabase) {

    val pokemons = db.dao.getAllPokemon()

    suspend fun getPokemonList() {
        val pokemonList = apiService.getPokemonList().results

        for (pokemonInResponse in pokemonList) {
            if (!db.dao.pokemonExists(pokemonInResponse.id)) {
                loadPokemon(pokemonInResponse)
            }
        }
    }

    fun getPokemon(id: Int): LiveData<Pokemon> {
        return db.dao.getPokemon(id)
    }

    fun getPokemonNonLiveData(id: Int): Pokemon {
        return db.dao.getPokemonNonLiveData(id)
    }

    suspend fun loadMoveType(url: String): MoveType {
        return apiService.getPokemonMove(url)
    }

    suspend fun loadPokemon(pokemonInResponse: PokemonInResponse) {
        val pokemonWithDetail = apiService.getPokemonByName(pokemonInResponse.name)
        val pokemonDb = PokemonDb(
            id = pokemonWithDetail.id,
            name = pokemonWithDetail.name,
            spriteUrl = pokemonWithDetail.sprites.front_default
        )
        db.dao.insertPokemon(pokemonDb)
        for (type in pokemonWithDetail.types) {
            db.dao.insertType(type.type)
            val crossRef = PokemonTypeCrossRef(pokemonWithDetail.id, type.type.name)
            db.dao.insertPokemonTypeCrossRef(crossRef)
        }

        for (moveInResponse in pokemonWithDetail.moves) {
            val move = moveInResponse.move
            db.dao.insertMove(move)
            val crossRef = PokemonMoveCrossRef(pokemonWithDetail.id, move.name)
            db.dao.insertPokemonMoveCrossRef(crossRef)
        }
    }
}

