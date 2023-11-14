package com.example.project_one_alpha.db.datamodel

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

class Pokemon (
    @Embedded
    open var pokemonDb: PokemonDb,

    @Relation(
        parentColumn = "id",
        entityColumn = "name",
        associateBy = Junction(
            value = PokemonTypeCrossRef::class,
            parentColumn = "pokemonId",
            entityColumn = "pokemonTypeName"
        )
    )


    var types: List<PokemonType>,


    @Relation(
        parentColumn = "id",
        entityColumn = "name",
        associateBy = Junction(
            value = PokemonMoveCrossRef::class,
            parentColumn = "pokemonId",
            entityColumn = "pokemonMoveName"
        )
    )
    var pokemonMove: List<PokemonMove>
)