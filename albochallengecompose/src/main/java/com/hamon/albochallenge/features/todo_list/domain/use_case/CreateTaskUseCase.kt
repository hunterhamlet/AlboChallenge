package com.hamon.albochallenge.features.todo_list.domain.use_case

import com.hamon.albochallenge.features.todo_list.data.model.TaskData
import com.hamon.albochallenge.features.todo_list.domain.entities.TaskDomain
import com.hamon.albochallenge.features.todo_list.domain.repository.TodoListRepository

interface CreateTaskUseCase {
    suspend operator fun invoke(task: TaskDomain): Boolean
}

class CreateTaskUseCaseImpl(private val todoListRepository: TodoListRepository) :
    CreateTaskUseCase {
    override suspend fun invoke(task: TaskDomain): Boolean =
        todoListRepository.create(TaskData(title = task.title, description = task.description))

}