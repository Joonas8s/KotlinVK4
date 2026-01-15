package com.example.vkt1.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.vkt1.domain.*
import java.time.LocalDate

@Composable
fun HomeScreen() {
    var tasks by remember { mutableStateOf(mockTasks) }
    var showOnlyDone by remember { mutableStateOf<Boolean?>(null) } // null = all, true = done, false = not done

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Tehtävälista",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = {
                val newTask = Task(
                    id = (tasks.maxOfOrNull { it.id } ?: 0) + 1,
                    title = "Uusi tehtävä",
                    description = "Kuvaus tähän",
                    priority = Priority.MEDIUM,
                    dueDate = LocalDate.now().plusDays(7)
                )
                tasks = addTask(tasks, newTask)
            }) {
                Text("Lisää")
            }

            Button(onClick = {
                tasks = sortByDueDate(tasks)
            }) {
                Text("Järjestä")
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = { showOnlyDone = null }) { Text("Kaikki") }
            Button(onClick = { showOnlyDone = true }) { Text("Tehdyt") }
            Button(onClick = { showOnlyDone = false }) { Text("Kesken") }
        }

        val displayedTasks = when (showOnlyDone) {
            true -> filterByDone(tasks, true)
            false -> filterByDone(tasks, false)
            null -> tasks
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(displayedTasks) { task ->
                TaskRow(task = task, onToggle = {
                    tasks = toggleDone(tasks, task.id)
                })
            }
        }
    }
}

@Composable
fun TaskRow(task: Task, onToggle: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onToggle
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = task.title, style = MaterialTheme.typography.titleSmall)
                Text(text = task.description, style = MaterialTheme.typography.bodySmall)
                Text(
                    text = "Pvm: ${task.dueDate} | Prioriteetti: ${task.priority}",
                    style = MaterialTheme.typography.labelSmall
                )
            }
            Checkbox(checked = task.done, onCheckedChange = { onToggle() })
        }
    }
}
