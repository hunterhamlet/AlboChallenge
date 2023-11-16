package com.hamon.albochallenge.features.todo_list.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamon.albochallenge.features.todo_list.domain.entities.TaskDomain
import com.hamon.albochallenge.features.todo_list.domain.use_case.CreateTaskUseCase
import com.hamon.albochallenge.features.todo_list.domain.use_case.DeleteTaskUseCase
import com.hamon.albochallenge.features.todo_list.domain.use_case.GetAllTaskUseCase
import com.hamon.albochallenge.features.todo_list.domain.use_case.ReadTaskUseCase
import com.hamon.albochallenge.features.todo_list.domain.use_case.UpdateTaskUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ToDoListViewModel(
    private val createTaskUseCase: CreateTaskUseCase,
    private val readTaskUseCase: ReadTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val getAllTaskUseCase: GetAllTaskUseCase
) : ViewModel() {

    private val _todoListObservable = MutableLiveData<MutableList<TaskDomain>>()
    val todoListObservable: LiveData<MutableList<TaskDomain>> get() = _todoListObservable

    private val _todoTaskRead = MutableLiveData<TaskDomain>()
    val todoTaskRead: LiveData<TaskDomain> get() = _todoTaskRead

    fun createTask(title: String, description: String) {
        viewModelScope.launch(Dispatchers.IO) {
            createTaskUseCase.invoke(TaskDomain(title = title, description = description))
            getAllTask()
        }
    }

    fun updateTask(taskDomain: TaskDomain) {
        viewModelScope.launch(Dispatchers.IO) {
            updateTaskUseCase.invoke(taskDomain)
            getAllTask()
        }
    }

    fun deleteTask(taskId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteTaskUseCase.invoke(taskId)
            getAllTask()
        }
    }

    fun readTask(taskId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = readTaskUseCase.invoke(taskId)
            _todoTaskRead.postValue(result)
        }
    }

    fun getAllTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = getAllTaskUseCase.invoke()
            _todoListObservable.postValue(result)
        }
    }

}