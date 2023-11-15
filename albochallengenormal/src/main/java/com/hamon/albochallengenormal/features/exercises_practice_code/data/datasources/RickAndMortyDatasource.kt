package com.hamon.albochallengenormal.features.exercises_practice_code.data.datasources

import com.hamon.albochallengenormal.core.network.Client.getServices
import com.hamon.albochallengenormal.core.service.RickAndMortyServices
import com.hamon.albochallengenormal.features.exercises_practice_code.data.model.RickAndMortyCharacterResponse
import retrofit2.Retrofit


interface RickAndMortyDatasource {
    suspend fun getCharacters(): MutableList<RickAndMortyCharacterResponse>
    suspend fun getCharactersDeferred(): MutableList<RickAndMortyCharacterResponse>

}

class RickAndMortyDatasourceImpl(
    private val client: Retrofit,
    private val clientDeferred: Retrofit
) : RickAndMortyDatasource {
    override suspend fun getCharacters(): MutableList<RickAndMortyCharacterResponse> {
        runCatching {
            val result = client.getServices<RickAndMortyServices>().getCharacters()
            return if (result.isSuccessful) {
                result.body()?.results?.filterNotNull()?.toMutableList() ?: mutableListOf()
            } else {
                mutableListOf()
            }

        }.getOrElse {
            return mutableListOf()
        }
    }

    override suspend fun getCharactersDeferred(): MutableList<RickAndMortyCharacterResponse> {
        runCatching {
            val result =
                clientDeferred.getServices<RickAndMortyServices>().getCharactersCoroutines().await()
            return result.results?.filterNotNull()?.toMutableList() ?: mutableListOf()
        }.getOrElse {
            return mutableListOf()
        }
    }

}