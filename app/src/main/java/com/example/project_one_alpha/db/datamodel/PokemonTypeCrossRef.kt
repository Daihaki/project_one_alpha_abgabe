package com.example.project_one_alpha.db.datamodel

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    primaryKeys = ["pokemonId", "pokemonTypeName"],
    foreignKeys = [
        ForeignKey(
            entity = PokemonDb::class,
            parentColumns = ["id"],
            childColumns = ["pokemonId"]
        ),
        ForeignKey(
            entity = PokemonType::class,
            parentColumns = ["name"],
            childColumns = ["pokemonTypeName"]
        )
    ]
)
data class PokemonTypeCrossRef(
    val pokemonId: Int,
    val pokemonTypeName: String,
)