package com.hamon.albochallenge.features.todo_list.domain.entities

data class TaskDomain (
    val id: Long? = null,
    val title: String,
    val description: String
)