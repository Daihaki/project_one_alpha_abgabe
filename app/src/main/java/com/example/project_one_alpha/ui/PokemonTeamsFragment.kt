package com.example.project_one_alpha.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import coil.load
import com.example.project_one_alpha.MainViewModel
import com.example.project_one_alpha.R
import com.example.project_one_alpha.databinding.FragmentPokemonTeamsBinding

class PokemonTeamsFragment : Fragment(), PokemonSelectionFragment.OnSelectionListener {
    private lateinit var binding: FragmentPokemonTeamsBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPokemonTeamsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.selectedPokemon.observe(viewLifecycleOwner) { state ->
            state.position?.let { position ->
                binding.gridLayout.getChildAt(position)?.let {
                    it.findViewById<ImageView>(R.id.add_pokemon_button).isVisible = false
                    it.findViewById<ImageView>(R.id.poke_image).load(state.pokemon?.pokemonDb?.spriteUrl)
                }
            }
        }

        (0 until binding.gridLayout.childCount).map { index ->
            binding.gridLayout.getChildAt(index)
                ?.findViewById<ImageView>(R.id.add_pokemon_button)
                ?.setOnClickListener {
                    activity?.supportFragmentManager?.let {
                        PokemonSelectionFragment(index, this).show(it, "PokemonSelectionFragmentTag")
                    }
            }
        }
    }

    override fun onSelected(id: Int, position: Int) {
        viewModel.loadPokemonForSelection(id, position)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.resetSelectedPokemon()
    }
}