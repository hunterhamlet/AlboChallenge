package com.hamon.albochallengenormal.features.exercises_practice_code.domain.repositories

import com.hamon.albochallengenormal.features.exercises_practice_code.data.model.RickAndMortyCharacterResponse

interface RickAndMortyRepository {
    suspend fun getCharacters(): MutableList<RickAndMortyCharacterResponse>
    suspend fun getCharactersDeferred(): MutableList<RickAndMortyCharacterResponse>
}