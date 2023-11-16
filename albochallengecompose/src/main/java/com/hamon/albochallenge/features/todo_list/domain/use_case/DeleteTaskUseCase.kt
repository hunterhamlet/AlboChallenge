package com.hamon.albochallenge.features.todo_list.domain.use_case

import com.hamon.albochallenge.features.todo_list.domain.repository.TodoListRepository

interface DeleteTaskUseCase {
    suspend operator fun invoke(taskId: Long): Boolean
}

class DeleteTaskUseCaseImpl(private val repository: TodoListRepository) : DeleteTaskUseCase {
    override suspend fun invoke(taskId: Long): Boolean = repository.delete(taskId)

}