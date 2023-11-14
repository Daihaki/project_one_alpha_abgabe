package com.example.project_one_alpha.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.project_one_alpha.MainViewModel
import com.example.project_one_alpha.databinding.PokedexFragmentBinding
import com.example.project_one_alpha.ui.adapter.PokemonAdapter
import com.example.project_one_alpha.ui.adapter.PokemonClickListener

class PokedexFragment : Fragment(), PokemonClickListener {

    private lateinit var binding: PokedexFragmentBinding
    private val viewmodel: MainViewModel by activityViewModels()

    override fun onPokemonClick(pokemonID: Int) {
        Log.d("Pokemon App", "Pokemon with ID $pokemonID clicked")
        val navController = findNavController()
        navController.navigate(PokedexFragmentDirections.actionPokedexFragmentToPokemonDetailsFragment(pokemonID))

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = PokedexFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel.pokemons.observe(viewLifecycleOwner) {
            binding.pokemonRV.adapter = PokemonAdapter(it, this)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}