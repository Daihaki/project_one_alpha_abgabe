package com.example.project_one_alpha.api.datamodel

data class PokemonInResponse(
    val name: String,
    val url: String,
) {
    private val split = url.split('/')
    val id = split[split.size-2].toInt()
}