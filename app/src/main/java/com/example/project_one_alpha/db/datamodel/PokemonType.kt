package com.example.project_one_alpha.db.datamodel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PokemonType(
    @PrimaryKey
    val name: String,
    val url: String
)