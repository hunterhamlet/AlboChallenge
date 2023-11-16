package com.hamon.albochallenge.features.todo_list.data.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class TaskData(
    @Id var id: Long = 0,
    var title: String,
    var description: String
)