package com.hamon.albochallengenormal.features.exercises_practice_code.data.model


import com.google.gson.annotations.SerializedName

data class RickAndMortyCharacterResponse(
    @SerializedName("gender")
    val gender: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("species")
    val species: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("type")
    val type: String?
)