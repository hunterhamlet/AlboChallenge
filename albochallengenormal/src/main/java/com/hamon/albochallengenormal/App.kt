package com.hamon.albochallengenormal

import android.app.Application
import com.hamon.albochallengenormal.core.di.client
import com.hamon.albochallengenormal.core.di.datasources
import com.hamon.albochallengenormal.core.di.repositories
import com.hamon.albochallengenormal.core.di.useCases
import com.hamon.albochallengenormal.core.di.viewModels
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(
                listOf(
                    client, datasources, repositories, viewModels, useCases
                )
            )
        }
    }

}