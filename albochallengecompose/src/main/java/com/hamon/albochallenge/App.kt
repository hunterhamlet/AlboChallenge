package com.hamon.albochallenge

import android.app.Application
import com.hamon.albochallenge.core.di.dataBases
import com.hamon.albochallenge.core.di.datasource
import com.hamon.albochallenge.core.di.repository
import com.hamon.albochallenge.core.di.useCases
import com.hamon.albochallenge.core.di.viewModels
import com.hamon.albochallenge.core.local_datasource.TaskLocalDatasource
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        TaskLocalDatasource.init(this)
        startKoin {
            modules(listOf(dataBases, datasource, repository, useCases, viewModels))
        }
    }
}