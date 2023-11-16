package com.hamon.albochallenge.features.todo_list.data.repository

import com.hamon.albochallenge.features.todo_list.data.datasource.TodoListLocalDataSource
import com.hamon.albochallenge.features.todo_list.data.model.TaskData
import com.hamon.albochallenge.features.todo_list.domain.repository.TodoListRepository

class TodoListRepositoryImpl(private val dataSource: TodoListLocalDataSource) : TodoListRepository {
    override suspend fun create(task: TaskData): Boolean = dataSource.create(task)

    override suspend fun read(taskId: Long): TaskData? = dataSource.read(taskId)

    override suspend fun readAll(): MutableList<TaskData> = dataSource.readAll()

    override suspend fun update(task: TaskData): Boolean = dataSource.update(task)

    override suspend fun delete(taskId: Long): Boolean = dataSource.delete(taskId)
}