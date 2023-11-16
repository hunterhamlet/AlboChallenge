package com.hamon.albochallenge.features.todo_list.domain.repository

import com.hamon.albochallenge.features.todo_list.data.model.TaskData

interface TodoListRepository {
    suspend fun create(task: TaskData): Boolean
    suspend fun read(taskId: Long): TaskData?

    suspend fun readAll(): MutableList<TaskData>
    suspend fun update(task: TaskData): Boolean
    suspend fun delete(taskId: Long): Boolean
}