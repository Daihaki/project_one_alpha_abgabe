package com.example.project_one_alpha

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.project_one_alpha.api.PokeApi
import com.example.project_one_alpha.db.datamodel.MoveType
import com.example.project_one_alpha.db.datamodel.Pokemon
import com.example.project_one_alpha.db.datamodel.PokemonDb
import com.example.project_one_alpha.db.datamodel.PokemonMove
import com.example.project_one_alpha.db.getDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel (app: Application) : AndroidViewModel(app) {

    private val repository = Repository(PokeApi.retrofitService, getDatabase(app))
    val pokemons: LiveData<List<Pokemon>> = repository.pokemons

    private val _moves = MutableLiveData<LoadMovesState>()
    val moves: LiveData<LoadMovesState> = _moves

    private val _selectedPokemon = MutableLiveData<SelectedPokemonState>()
    val selectedPokemon: LiveData<SelectedPokemonState> = _selectedPokemon

    val selectedPokemonsForTeam: MutableList<Int> = mutableListOf()

    init {
        loadPokeList()
    }

    fun loadPokemonWithId(id: Int): LiveData<Pokemon> {
        return repository.getPokemon(id)
    }

    fun loadMoves(moves: List<PokemonMove>) {
        if (moves.isEmpty()) {
            return
        }

        _moves.value = LoadMovesState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            val allMoves = moves.map {
                async {
                    repository.loadMoveType(it.url)
                }
            }.awaitAll()

            withContext(Dispatchers.Main) {
                _moves.value = LoadMovesState.OnFinishedLoading(allMoves)
            }
        }
    }

    private fun loadPokeList(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getPokemonList()
        }
    }

    fun loadPokemonForSelection(id: Int, position: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val pokemon = repository.getPokemonNonLiveData(id)
            withContext(Dispatchers.Main) {
                selectedPokemonsForTeam.add(pokemon.pokemonDb.id)
                _selectedPokemon.value = SelectedPokemonState(pokemon, position)
            }
        }
    }

    fun setLoading() {
        _moves.value = LoadMovesState.Loading
    }

    fun resetSelectedPokemon() {
        _selectedPokemon.value = SelectedPokemonState(null, null)
        selectedPokemonsForTeam.clear()
    }
}

sealed class LoadMovesState {
    data object Loading : LoadMovesState()
    data class OnFinishedLoading(val moves: List<MoveType>) : LoadMovesState()
}

data class SelectedPokemonState(val pokemon: Pokemon?, val position: Int?)

