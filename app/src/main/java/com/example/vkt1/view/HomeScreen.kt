package com.example.vkt1.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vkt1.model.Priority
import com.example.vkt1.model.Task
import com.example.vkt1.viewmodel.TaskViewModel
import java.time.LocalDate

@Composable
fun HomeScreen(viewModel: TaskViewModel = viewModel()) {
    val tasks by viewModel.tasks.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var selectedTask by remember { mutableStateOf<Task?>(null) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                selectedTask = null
                showDialog = true
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add Task")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp)) {
            Text("Tehtävälista (MVVM)", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(tasks) { task ->
                    TaskItem(
                        task = task,
                        onToggle = { viewModel.toggleDone(task.id) },
                        onEdit = {
                            selectedTask = task
                            showDialog = true
                        },
                        onDelete = { viewModel.removeTask(task.id) }
                    )
                }
            }
        }

        if (showDialog) {
            DetailDialog(
                task = selectedTask,
                onDismiss = { showDialog = false },
                onSave = { task ->
                    if (selectedTask == null) {
                        viewModel.addTask(task.copy(id = (tasks.maxOfOrNull { it.id } ?: 0) + 1))
                    } else {
                        viewModel.updateTask(task)
                    }
                    showDialog = false
                }
            )
        }
    }
}

@Composable
fun TaskItem(task: Task, onToggle: () -> Unit, onEdit: () -> Unit, onDelete: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                Checkbox(checked = task.done, onCheckedChange = { onToggle() })
                Column {
                    Text(task.title, style = MaterialTheme.typography.titleMedium)
                    Text(task.dueDate.toString(), style = MaterialTheme.typography.bodySmall)
                }
            }
            Row {
                IconButton(onClick = onEdit) { Icon(Icons.Default.Edit, "Edit") }
                IconButton(onClick = onDelete) { Icon(Icons.Default.Delete, "Delete") }
            }
        }
    }
}

@Composable
fun DetailDialog(task: Task?, onDismiss: () -> Unit, onSave: (Task) -> Unit) {
    var title by remember { mutableStateOf(task?.title ?: "") }
    var description by remember { mutableStateOf(task?.description ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (task == null) "Lisää tehtävä" else "Muokkaa tehtävää") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Otsikko") })
                OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Kuvaus") })
            }
        },
        confirmButton = {
            Button(onClick = {
                onSave(
                    task?.copy(title = title, description = description)
                        ?: Task(0, title, description, Priority.MEDIUM, LocalDate.now())
                )
            }) { Text("Tallenna") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Peruuta") } }
    )
}
