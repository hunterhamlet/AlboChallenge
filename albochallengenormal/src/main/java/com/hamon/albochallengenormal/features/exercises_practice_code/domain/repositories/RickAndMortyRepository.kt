package com.hamon.albochallengenormal.features.exercises_practice_code.domain.repositories

interface RickAndMortyRepository {
    suspend fun getCharacters()
    suspend fun getCharactersDeferred()
}