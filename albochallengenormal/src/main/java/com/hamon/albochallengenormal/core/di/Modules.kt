package com.hamon.albochallengenormal.core.di

import com.hamon.albochallengenormal.core.network.Client
import com.hamon.albochallengenormal.core.network.Client.DEFERRED
import com.hamon.albochallengenormal.features.exercises_practice_code.data.datasources.RickAndMortyDatasource
import com.hamon.albochallengenormal.features.exercises_practice_code.data.datasources.RickAndMortyDatasourceImpl
import com.hamon.albochallengenormal.features.exercises_practice_code.data.repositories.RickAndMortyRepositoryImpl
import com.hamon.albochallengenormal.features.exercises_practice_code.domain.repositories.RickAndMortyRepository
import com.hamon.albochallengenormal.features.exercises_practice_code.domain.usecase.GetCharacterDeferredUseCase
import com.hamon.albochallengenormal.features.exercises_practice_code.domain.usecase.GetCharacterDeferredUseCaseImpl
import com.hamon.albochallengenormal.features.exercises_practice_code.domain.usecase.GetCharacterUseCase
import com.hamon.albochallengenormal.features.exercises_practice_code.domain.usecase.GetCharacterUseCaseImpl
import com.hamon.albochallengenormal.features.exercises_practice_code.presentation.view_models.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val client = module {
    single { Client.getClient() }
    single(named(DEFERRED)) { Client.getClientDeferred() }
}

val datasources = module {
    single { RickAndMortyDatasourceImpl(get(), get(named(DEFERRED))) as RickAndMortyDatasource }
}

val repositories = module {
    single { RickAndMortyRepositoryImpl(get()) as RickAndMortyRepository }
}

val useCases = module {
    singleOf(::GetCharacterUseCaseImpl) { bind<GetCharacterUseCase>() }
    single<GetCharacterDeferredUseCase> { GetCharacterDeferredUseCaseImpl(get()) }
}

val viewModels = module {
    viewModelOf(::MainViewModel)
}