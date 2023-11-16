import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.hamon.albochallenge.features.todo_list.domain.entities.TaskDomain
import com.hamon.albochallenge.features.todo_list.presentation.view_model.ToDoListViewModel
import org.koin.compose.koinInject

@Composable
fun ListToTaskScreen(viewModel: ToDoListViewModel = koinInject()) {

    val todoList by viewModel.todoListObservable.observeAsState()
    var showDialog by remember { mutableStateOf(false) }
    var taskToEdit: TaskDomain? by remember { mutableStateOf(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var taskToDeleteId: Long? by remember { mutableStateOf(null) }

    LaunchedEffect(key1 = null) {
        viewModel.getAllTask()
    }

    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {
            showDialog = true
        }) {
            Icon(Icons.Outlined.Add, "Add new task")
        }
    }) { _ ->
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(todoList?.toList() ?: mutableListOf()) {
                    TaskRow(taskDomain = it, deleteTask = { taskId ->
                        showDeleteDialog = true
                        taskToDeleteId = taskId
                    }, taskToEdit = { taskDomain ->
                        taskToEdit = taskDomain
                        showDialog = true
                    })
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Delete task
            if (showDeleteDialog) {
                DeleteTask(onDismissRequest = {
                    showDeleteDialog = false
                    taskToDeleteId = null
                }) {
                    taskToDeleteId?.let {
                        viewModel.deleteTask(it)
                    }
                    showDeleteDialog = false
                    taskToDeleteId = null
                }
            }


            // Show Dialog for create or edit
            if (showDialog) {
                AddUpdateUser(task = taskToEdit,
                    onDismissRequest = {
                        showDialog = false
                        taskToEdit = null
                    }) { task ->
                    if (task.id != null) {
                        viewModel.updateTask(task)
                    } else {
                        viewModel.createTask(task.title, task.description)
                    }
                    showDialog = false
                    taskToEdit = null
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskRow(taskDomain: TaskDomain, taskToEdit: (TaskDomain) -> Unit, deleteTask: (Long?) -> Unit) {
    val haptics = LocalHapticFeedback.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .combinedClickable(onClick = {
                    taskToEdit.invoke(taskDomain)
                }, onLongClick = {
                    haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                    deleteTask.invoke(taskDomain.id)
                }, onLongClickLabel = "Delete task")
                .padding(16.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = taskDomain.title,
                style = TextStyle(fontSize = 18.sp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(modifier = Modifier.fillMaxWidth(), text = taskDomain.description)
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteTask(onDismissRequest: () -> Unit, onConfirmation: () -> Unit) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Estas a punto de eliminar una nota.",
                    textAlign = TextAlign.Center,
                    style = TextStyle(fontSize = 18.sp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Button(modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        border = BorderStroke(
                            width = 1.dp, color = MaterialTheme.colorScheme.primary
                        ),
                        onClick = { onDismissRequest() }) {
                        Text(text = "Cancelar", color = MaterialTheme.colorScheme.primary)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(modifier = Modifier.weight(1f), onClick = {
                        onConfirmation.invoke()
                    }) {
                        Text(text = "Aceptar")
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun DeleteTaskPreview() {
    DeleteTask(onDismissRequest = {}) {

    }
}

@Composable
fun AddUpdateUser(
    task: TaskDomain? = null, onDismissRequest: () -> Unit, onConfirmation: (TaskDomain) -> Unit
) {

    var titleText by remember { mutableStateOf(task?.title ?: "") }
    var descriptionText by remember { mutableStateOf(task?.description ?: "") }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = if (task == null) "Agrega una nueva nota." else "Editar nota.",
                    textAlign = TextAlign.Center,
                    style = TextStyle(fontSize = 18.sp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = titleText, onValueChange = { titleText = it }, label = {
                    Text(
                        text = "Titulo"
                    )
                }, shape = RoundedCornerShape(16.dp))
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = descriptionText,
                    onValueChange = { descriptionText = it },
                    label = {
                        Text(
                            text = "Descripción"
                        )
                    },
                    shape = RoundedCornerShape(16.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Button(modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        border = BorderStroke(
                            width = 1.dp, color = MaterialTheme.colorScheme.primary
                        ),
                        onClick = { onDismissRequest() }) {
                        Text(text = "Cancelar", color = MaterialTheme.colorScheme.primary)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(modifier = Modifier.weight(1f), onClick = {
                        onConfirmation(
                            TaskDomain(
                                id = task?.id,
                                title = titleText,
                                description = descriptionText
                            )
                        )
                    }) {
                        Text(text = "Aceptar")
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun AddUpdateUserPreview() {
    AddUpdateUser(onDismissRequest = { }, onConfirmation = { task ->

    })
}

@Composable
@Preview(showSystemUi = true)
fun ListToTaskScreenPreview() {
    ListToTaskScreen()
}

@Composable
@Preview(showSystemUi = true)
fun ListRowPreview() {
    TaskRow(
        taskDomain = TaskDomain(title = "Hola Mundo", description = "Hola mundo mas largo"),
        taskToEdit = {},
        deleteTask = {})
}