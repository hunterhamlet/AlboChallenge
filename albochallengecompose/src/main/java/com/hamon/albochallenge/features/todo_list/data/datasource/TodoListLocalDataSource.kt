package com.hamon.albochallenge.features.todo_list.data.datasource

import com.hamon.albochallenge.features.todo_list.data.model.TaskData
import io.objectbox.Box

interface TodoListLocalDataSource {
    suspend fun create(task: TaskData): Boolean
    suspend fun read(taskId: Long): TaskData?

    suspend fun readAll(): MutableList<TaskData>
    suspend fun update(task: TaskData): Boolean
    suspend fun delete(taskId: Long): Boolean
}

class TodoListLocalDataSourceImpl(private val dataBase: Box<TaskData>) : TodoListLocalDataSource {
    override suspend fun create(task: TaskData): Boolean {
        runCatching {
            dataBase.put(task)
            return true
        }.getOrElse {
            return false
        }
    }

    override suspend fun read(taskId: Long): TaskData? {
        runCatching {
            return dataBase[taskId]
        }.getOrElse {
            return null
        }
    }

    override suspend fun readAll(): MutableList<TaskData> {
        runCatching {
            return dataBase.all
        }.getOrElse {
            return mutableListOf()
        }
    }

    override suspend fun update(task: TaskData): Boolean {
        runCatching {
            dataBase.put(task)
            return true
        }.getOrElse {
            return false
        }
    }

    override suspend fun delete(taskId: Long): Boolean {
        runCatching {
            dataBase.remove(taskId)
            return true
        }.getOrElse {
            return false
        }
    }

}