package com.example.project_one_alpha.api.datamodel

import com.example.project_one_alpha.db.datamodel.PokemonMove
import com.example.project_one_alpha.db.datamodel.PokemonType


data class PokemonApi(
    val id: Int,
    val name: String,
    val sprites: Sprites,
    val types: List<TypeWithSlot>,
    val moves: List<MoveInResponse>
)

data class TypeWithSlot(
    val slot: Int,
    val type: PokemonType
)

data class MoveInResponse(
    val move: PokemonMove
)

data class Sprites(
    val front_default: String,
)

