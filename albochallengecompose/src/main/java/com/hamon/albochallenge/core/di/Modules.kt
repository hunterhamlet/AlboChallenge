package com.hamon.albochallenge.core.di

import com.hamon.albochallenge.core.local_datasource.TaskLocalDatasource
import com.hamon.albochallenge.features.todo_list.data.datasource.TodoListLocalDataSource
import com.hamon.albochallenge.features.todo_list.data.datasource.TodoListLocalDataSourceImpl
import com.hamon.albochallenge.features.todo_list.data.model.TaskData
import com.hamon.albochallenge.features.todo_list.data.repository.TodoListRepositoryImpl
import com.hamon.albochallenge.features.todo_list.domain.repository.TodoListRepository
import com.hamon.albochallenge.features.todo_list.domain.use_case.CreateTaskUseCase
import com.hamon.albochallenge.features.todo_list.domain.use_case.CreateTaskUseCaseImpl
import com.hamon.albochallenge.features.todo_list.domain.use_case.DeleteTaskUseCase
import com.hamon.albochallenge.features.todo_list.domain.use_case.DeleteTaskUseCaseImpl
import com.hamon.albochallenge.features.todo_list.domain.use_case.GetAllTaskUseCase
import com.hamon.albochallenge.features.todo_list.domain.use_case.GetAllTaskUseCaseImpl
import com.hamon.albochallenge.features.todo_list.domain.use_case.ReadTaskUseCase
import com.hamon.albochallenge.features.todo_list.domain.use_case.ReadTaskUseCaseImpl
import com.hamon.albochallenge.features.todo_list.domain.use_case.UpdateTaskUseCase
import com.hamon.albochallenge.features.todo_list.domain.use_case.UpdateTaskUseCaseImpl
import com.hamon.albochallenge.features.todo_list.presentation.view_model.ToDoListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataBases = module {
    single { TaskLocalDatasource.store.boxFor(TaskData::class.java) }
}

val datasource = module {
    single<TodoListLocalDataSource> { TodoListLocalDataSourceImpl(get()) }
}

val repository = module {
    single<TodoListRepository> { TodoListRepositoryImpl(get()) }
}

val useCases = module {
    single<CreateTaskUseCase> { CreateTaskUseCaseImpl(get()) }
    single<ReadTaskUseCase> { ReadTaskUseCaseImpl(get()) }
    single<UpdateTaskUseCase> { UpdateTaskUseCaseImpl(get()) }
    single<DeleteTaskUseCase> { DeleteTaskUseCaseImpl(get()) }
    single<GetAllTaskUseCase> { GetAllTaskUseCaseImpl(get()) }
}

val viewModels = module {
    viewModel { ToDoListViewModel(get(), get(), get(), get(), get()) }
}