package com.example.project_one_alpha.db.datamodel

data class MoveType(
    val name: String?,
    val power: Int?,
    val type: Type?
)

data class Type(val name: String?)
