package com.hamon.albochallenge.features.todo_list.domain.use_case

import com.hamon.albochallenge.features.todo_list.domain.entities.TaskDomain
import com.hamon.albochallenge.features.todo_list.domain.repository.TodoListRepository

interface GetAllTaskUseCase {

    suspend operator fun invoke(): MutableList<TaskDomain>
}

class GetAllTaskUseCaseImpl(private val repository: TodoListRepository) : GetAllTaskUseCase {
    override suspend fun invoke(): MutableList<TaskDomain> = repository.readAll()
        .map { TaskDomain(id = it.id, title = it.title, description = it.description) }
        .toMutableList()

}