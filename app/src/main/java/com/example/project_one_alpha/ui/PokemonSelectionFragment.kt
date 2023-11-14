package com.example.project_one_alpha.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_one_alpha.MainViewModel
import com.example.project_one_alpha.R
import com.example.project_one_alpha.databinding.FragmentPokemonSelectionBinding
import com.example.project_one_alpha.ui.adapter.PokemonAdapter
import com.example.project_one_alpha.ui.adapter.PokemonClickListener

class PokemonSelectionFragment(
    private val position: Int,
    private val onSelectionListener: OnSelectionListener
) : DialogFragment() {

    interface OnSelectionListener {
        fun onSelected(id: Int, position: Int)
    }

    private lateinit var binding: FragmentPokemonSelectionBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPokemonSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.pokemons.observe(viewLifecycleOwner) {
            binding.selectionRecyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext()).apply {
                    orientation = LinearLayoutManager.VERTICAL
                }

                val list = viewModel.selectedPokemonsForTeam
                val filteredPokemons = it.filterNot {
                    list.contains(it.pokemonDb.id)
                }

                adapter = PokemonAdapter(filteredPokemons, object : PokemonClickListener {
                    override fun onPokemonClick(pokemonID: Int) {
                        onSelectionListener.onSelected(pokemonID, position)
                        dismiss()
                    }
                })
            }
        }

        binding.selectionClose.setOnClickListener { dismiss() }
    }

    override fun getTheme(): Int {
        return R.style.DialogFragment
    }
}