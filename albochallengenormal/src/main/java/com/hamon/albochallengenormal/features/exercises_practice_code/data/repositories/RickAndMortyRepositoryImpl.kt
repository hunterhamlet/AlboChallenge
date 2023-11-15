package com.hamon.albochallengenormal.features.exercises_practice_code.data.repositories

import com.hamon.albochallengenormal.features.exercises_practice_code.data.datasources.RickAndMortyDatasource
import com.hamon.albochallengenormal.features.exercises_practice_code.data.model.RickAndMortyCharacterResponse
import com.hamon.albochallengenormal.features.exercises_practice_code.domain.repositories.RickAndMortyRepository

class RickAndMortyRepositoryImpl(private val datasource: RickAndMortyDatasource): RickAndMortyRepository {
    override suspend fun getCharacters(): MutableList<RickAndMortyCharacterResponse> {
        return datasource.getCharacters()
    }

    override suspend fun getCharactersDeferred(): MutableList<RickAndMortyCharacterResponse> {
        return datasource.getCharactersDeferred()
    }
}