package com.hamon.albochallengenormal.features.exercises_practice_code.domain.entities


import com.google.gson.annotations.SerializedName

data class RickAndMortyCharacter(
    val gender: String?,
    val id: Int?,
    val image: String?,
    val name: String?,
    val species: String?,
    val status: String?,
    val type: String?
)