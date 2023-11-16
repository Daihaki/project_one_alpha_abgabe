package com.example.project_one_alpha.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.project_one_alpha.databinding.PokemonItemBinding
import com.example.project_one_alpha.db.datamodel.Pokemon

class PokemonAdapter(
    val pokemonList: List<Pokemon>,
    private val clickListener: PokemonClickListener,
) : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    val germanPokeNames = mutableListOf<String>()




    inner class PokemonViewHolder(val binding: PokemonItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val pokemonId = pokemonList.getOrNull(adapterPosition)?.pokemonDb?.id

                pokemonId?.let {
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        clickListener.onPokemonClick(pokemonId)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding = PokemonItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {

        val pokemon = pokemonList[position]
        Log.d("PokemonLog", "$pokemon")

        holder.binding.pokemonIdTV.text = "#" + pokemon.pokemonDb.id.toString().padStart(3, '0')
        holder.binding.pokemonNameTV.text = pokemon.pokemonDb.name.replaceFirstChar { it.uppercase() }
        holder.binding.pokemonSpriteIV.load(pokemon.pokemonDb.spriteUrl)
    }
}