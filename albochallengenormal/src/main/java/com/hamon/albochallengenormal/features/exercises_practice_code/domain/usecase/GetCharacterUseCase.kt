package com.hamon.albochallengenormal.features.exercises_practice_code.domain.usecase

interface GetCharacterUseCase {
    suspend operator fun invoke()
}

class GetCharacterUseCaseImpl: GetCharacterUseCase {
    override suspend fun invoke() {
        TODO("Not yet implemented")
    }

}