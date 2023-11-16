package com.hamon.albochallenge.features.todo_list.domain.use_case

import com.hamon.albochallenge.features.todo_list.data.model.TaskData
import com.hamon.albochallenge.features.todo_list.domain.entities.TaskDomain
import com.hamon.albochallenge.features.todo_list.domain.repository.TodoListRepository

interface UpdateTaskUseCase {
    suspend operator fun invoke(task: TaskDomain): Boolean
}

class UpdateTaskUseCaseImpl(private val repository: TodoListRepository) : UpdateTaskUseCase {
    override suspend fun invoke(task: TaskDomain): Boolean =
        repository.update(
            TaskData(
                id = task.id ?: 0,
                title = task.title,
                description = task.description
            )
        )
}