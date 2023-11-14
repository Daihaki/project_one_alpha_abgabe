package com.example.project_one_alpha.db.datamodel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "pokemonDb")
data class PokemonDb (
    @PrimaryKey
    val id: Int,
    val name: String,
    val spriteUrl: String
)