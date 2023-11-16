package com.hamon.albochallengenormal.core.service

import com.hamon.albochallengenormal.core.network.CHARACTERS
import com.hamon.albochallengenormal.features.exercises_practice_code.data.model.RickAndMortyResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface RickAndMortyServices {

    @GET(CHARACTERS)
    suspend fun getCharacters(): Response<RickAndMortyResponse>

    @GET(CHARACTERS)
    suspend fun getCharactersCoroutines(): Deferred<RickAndMortyResponse>

}