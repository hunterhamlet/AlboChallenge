package com.hamon.albochallengenormal.core.service

import com.hamon.albochallengenormal.core.network.CHARACTERS
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface RickAndMortyServices {

    @GET(CHARACTERS)
    suspend fun getCharacters(): Response<String>

    @GET(CHARACTERS)
    suspend fun getCharactersCoroutines(): Deferred<String>

}