package com.hamon.albochallenge.features.todo_list.domain.use_case

import com.hamon.albochallenge.features.todo_list.domain.entities.TaskDomain
import com.hamon.albochallenge.features.todo_list.domain.repository.TodoListRepository

interface ReadTaskUseCase {
    suspend operator fun invoke(taskId: Long): TaskDomain?
}

class ReadTaskUseCaseImpl(private val todoListRepository: TodoListRepository) : ReadTaskUseCase {
    override suspend fun invoke(taskId: Long): TaskDomain? {
        todoListRepository.read(taskId)?.let {
            return TaskDomain(id = it.id, title = it.title, description = it.description)
        } ?: run {
            return null
        }
    }

}