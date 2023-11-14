package com.example.project_one_alpha.db.datamodel

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    primaryKeys = ["pokemonId", "pokemonMoveName"],
    foreignKeys = [
        ForeignKey(
            entity = PokemonDb::class,
            parentColumns = ["id"],
            childColumns = ["pokemonId"]
        ),
        ForeignKey(
            entity = PokemonMove::class,
            parentColumns = ["name"],
            childColumns = ["pokemonMoveName"]
        )
    ]
)
data class PokemonMoveCrossRef(
    val pokemonId: Int,
    val pokemonMoveName: String,
)