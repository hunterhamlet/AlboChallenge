package com.hamon.albochallengenormal.features.exercises_practice_code.presentation.view_models

import androidx.lifecycle.ViewModel
import com.hamon.albochallengenormal.features.exercises_practice_code.domain.usecase.GetCharacterDeferredUseCase
import com.hamon.albochallengenormal.features.exercises_practice_code.domain.usecase.GetCharacterUseCase

class MainViewModel(
    private val getCharacterUseCase: GetCharacterUseCase,
    private val getCharacterDeferredUseCase: GetCharacterDeferredUseCase
) : ViewModel() {
}