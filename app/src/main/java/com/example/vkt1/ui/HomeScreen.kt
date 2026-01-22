package com.example.vkt1.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vkt1.domain.Priority
import com.example.vkt1.domain.Task
import java.time.LocalDate

@Composable
fun HomeScreen(viewModel: TaskViewModel = viewModel()) {
    var newTaskTitle by remember { mutableStateOf("") }
    var showOnlyDone by remember { mutableStateOf<Boolean?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Viikko 2: Tehtävälista",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Lisäysosio
        Card(
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = newTaskTitle,
                    onValueChange = { newTaskTitle = it },
                    label = { Text("Uusi tehtävä") },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    if (newTaskTitle.isNotBlank()) {
                        viewModel.addTask(
                            title = newTaskTitle,
                            description = "",
                            priority = Priority.MEDIUM,
                            dueDate = LocalDate.now().plusDays(1)
                        )
                        newTaskTitle = ""
                    }
                }) {
                    Text("Lisää")
                }
            }
        }

        // Suodatus ja järjestys
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChip(
                selected = showOnlyDone == null,
                onClick = { showOnlyDone = null },
                label = { Text("Kaikki") }
            )
            FilterChip(
                selected = showOnlyDone == true,
                onClick = { showOnlyDone = true },
                label = { Text("Tehdyt") }
            )
            FilterChip(
                selected = showOnlyDone == false,
                onClick = { showOnlyDone = false },
                label = { Text("Kesken") }
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { viewModel.sortByDueDate() }) {
                Text("📅")
            }
        }

        val tasks = viewModel.getFilteredTasks(showOnlyDone)

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(tasks, key = { it.id }) { task ->
                TaskRow(
                    task = task,
                    onToggle = { viewModel.toggleDone(task.id) },
                    onDelete = { viewModel.removeTask(task.id) }
                )
            }
        }
    }
}

@Composable
fun TaskRow(task: Task, onToggle: () -> Unit, onDelete: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = task.done,
                onCheckedChange = { onToggle() }
            )
            Column(modifier = Modifier.weight(1f).padding(horizontal = 8.dp)) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = if (task.done) Color.Gray else Color.Unspecified
                )
                if (task.description.isNotBlank()) {
                    Text(text = task.description, style = MaterialTheme.typography.bodySmall)
                }
                Text(
                    text = "Deadline: ${task.dueDate} | ${task.priority}",
                    style = MaterialTheme.typography.labelSmall
                )
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Poista", tint = MaterialTheme.colorScheme.error)
            }
        }
    }
}
