package com.hamon.albochallengenormal.features.exercises_practice_code.data.model


import com.google.gson.annotations.SerializedName

data class RickAndMortyResponse(
    @SerializedName("results")
    val results: List<RickAndMortyCharacterResponse?>?
)