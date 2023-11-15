package com.hamon.albochallengenormal.features.exercises_practice_code.data.datasources

import retrofit2.Retrofit


interface RickAndMortyDatasource {
    suspend fun getCharacters()
    suspend fun getCharactersDeferred()

}

class RickAndMortyDatasourceImpl(
    private val client: Retrofit,
    private val clientDeferred: Retrofit
) : RickAndMortyDatasource {
    override suspend fun getCharacters() {

    }

    override suspend fun getCharactersDeferred() {
        TODO("Not yet implemented")
    }

}