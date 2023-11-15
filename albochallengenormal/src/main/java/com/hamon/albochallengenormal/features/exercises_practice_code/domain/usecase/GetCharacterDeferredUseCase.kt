package com.hamon.albochallengenormal.features.exercises_practice_code.domain.usecase

interface GetCharacterDeferredUseCase {
    suspend operator fun invoke()
}

class GetCharacterDeferredUseCaseImpl: GetCharacterDeferredUseCase {
    override suspend fun invoke() {
        TODO("Not yet implemented")
    }

}