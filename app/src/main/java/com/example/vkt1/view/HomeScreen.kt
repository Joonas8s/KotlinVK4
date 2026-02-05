package com.example.vkt1.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.vkt1.model.Priority
import com.example.vkt1.model.Task
import com.example.vkt1.viewmodel.TaskViewModel
import java.time.LocalDate
import java.time.format.DateTimeParseException

@Composable
fun HomeScreenContent(viewModel: TaskViewModel, padding: PaddingValues) {
    val tasks by viewModel.tasks.collectAsState()
    var showEditDialog by remember { mutableStateOf(false) }
    var selectedTask by remember { mutableStateOf<Task?>(null) }

    Column(modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp)) {
        Text("Tehtävälista", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(tasks) { task ->
                TaskItem(
                    task = task,
                    onToggle = { viewModel.toggleDone(task.id) },
                    onEdit = {
                        selectedTask = task
                        showEditDialog = true
                    },
                    onDelete = { viewModel.removeTask(task.id) }
                )
            }
        }
    }

    if (showEditDialog && selectedTask != null) {
        DetailDialog(
            task = selectedTask,
            onDismiss = { showEditDialog = false },
            onSave = { updatedTask ->
                viewModel.updateTask(updatedTask)
                showEditDialog = false
            }
        )
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
                    Text(
                        text = task.title,
                        style = MaterialTheme.typography.titleMedium,
                        textDecoration = if (task.done) androidx.compose.ui.text.style.TextDecoration.LineThrough else null
                    )
                    Text("${task.dueDate} | ${task.priority}", style = MaterialTheme.typography.bodySmall)
                }
            }
            Row {
                IconButton(onClick = onEdit) { Icon(Icons.Default.Edit, "Muokkaa") }
                IconButton(onClick = onDelete) { Icon(Icons.Default.Delete, "Poista") }
            }
        }
    }
}

@Composable
fun DetailDialog(task: Task?, onDismiss: () -> Unit, onSave: (Task) -> Unit) {
    var title by remember { mutableStateOf(task?.title ?: "") }
    var description by remember { mutableStateOf(task?.description ?: "") }
    var priority by remember { mutableStateOf(task?.priority ?: Priority.MEDIUM) }
    var dueDateString by remember { mutableStateOf(task?.dueDate?.toString() ?: LocalDate.now().toString()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (task == null) "Lisää tehtävä" else "Muokkaa tehtävää") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Otsikko") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Kuvaus") },
                    modifier = Modifier.fillMaxWidth()
                )
                
                Text("Prioriteetti:", style = MaterialTheme.typography.labelLarge)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Priority.entries.forEach { p ->
                        FilterChip(
                            selected = priority == p,
                            onClick = { priority = p },
                            label = { Text(p.name) }
                        )
                    }
                }
                
                OutlinedTextField(
                    value = dueDateString,
                    onValueChange = { dueDateString = it },
                    label = { Text("Määräpäivä (YYYY-MM-DD)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val date = try {
                        LocalDate.parse(dueDateString)
                    } catch (e: DateTimeParseException) {
                        LocalDate.now()
                    }
                    onSave(
                        task?.copy(title = title, description = description, priority = priority, dueDate = date)
                            ?: Task(0, title, description, priority, date)
                    )
                },
                enabled = title.isNotBlank()
            ) { Text("Tallenna") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Peruuta") } }
    )
}
