package com.example.project_one_alpha.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import coil.load
import com.example.project_one_alpha.LoadMovesState
import com.example.project_one_alpha.MainViewModel
import com.example.project_one_alpha.databinding.FragmentPokemonDetailsBinding
import com.example.project_one_alpha.ui.adapter.MoveAdapter

class PokemonDetailsFragment : Fragment() {
    private lateinit var binding: FragmentPokemonDetailsBinding
    private val args: PokemonDetailsFragmentArgs by navArgs()
    private val viewModel: MainViewModel by activityViewModels()
    private var moveAdapter: MoveAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPokemonDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadPokemonWithId(args.pokemonID).observe(viewLifecycleOwner) {
            viewModel.loadMoves(it.pokemonMove)

            binding.pokemonNameTV.text = it.pokemonDb.name
            binding.pokemonIdTV.text = "#" + it.pokemonDb.id.toString().padStart(3, '0')
            binding.pokemonSpriteIV.load(it.pokemonDb.spriteUrl)
            binding.type1TextView.text = it.types.getOrNull(0)?.name ?: "No type"
            binding.type2TextView.text = it.types.getOrNull(1)?.name ?: "No type"
        }

        viewModel.moves.observe(viewLifecycleOwner) {
            when (it) {
                LoadMovesState.Loading -> {
                    binding.movesProgressBar.visibility = View.VISIBLE
                    binding.pokemonMoveRV.visibility = View.GONE
                }
                is LoadMovesState.OnFinishedLoading -> {
                    binding.movesProgressBar.visibility = View.GONE
                    binding.pokemonMoveRV.visibility = View.VISIBLE

                    binding.pokemonMoveRV.apply {
                        layoutManager = GridLayoutManager(requireContext(), 2).apply {
                            orientation = GridLayoutManager.HORIZONTAL
                        }

                        if (moveAdapter == null) {
                            moveAdapter = MoveAdapter(it.moves)
                            adapter = moveAdapter
                        } else {
                            moveAdapter?.setItems(it.moves)
                        }
                    }
                }
            }
        }

        binding.button2.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.setLoading()
    }
}
