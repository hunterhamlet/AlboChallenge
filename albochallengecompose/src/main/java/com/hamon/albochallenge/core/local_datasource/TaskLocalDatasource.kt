package com.hamon.albochallenge.core.local_datasource

import android.content.Context
import com.hamon.albochallenge.features.todo_list.data.model.MyObjectBox
import io.objectbox.BoxStore

object TaskLocalDatasource {
    lateinit var store: BoxStore
        private set

    fun init(context: Context) {
        store = MyObjectBox.builder().androidContext(context.applicationContext).build()
    }
}