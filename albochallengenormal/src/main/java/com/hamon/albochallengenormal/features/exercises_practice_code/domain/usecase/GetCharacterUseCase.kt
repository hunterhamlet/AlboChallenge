package com.hamon.albochallengenormal.features.exercises_practice_code.domain.usecase

import com.hamon.albochallengenormal.features.exercises_practice_code.domain.entities.RickAndMortyCharacter
import com.hamon.albochallengenormal.features.exercises_practice_code.domain.repositories.RickAndMortyRepository

interface GetCharacterUseCase {
    suspend operator fun invoke(): MutableList<RickAndMortyCharacter>
}

class GetCharacterUseCaseImpl(private val repository: RickAndMortyRepository) :
    GetCharacterUseCase {
    override suspend fun invoke(): MutableList<RickAndMortyCharacter> =
        repository.getCharacters().mapNotNull {
            RickAndMortyCharacter(
                gender = it.gender,
                id = it.id,
                image = it.image,
                name = it.name,
                species = it.species,
                status = it.status,
                type = it.type
            )
        }.toMutableList()

}