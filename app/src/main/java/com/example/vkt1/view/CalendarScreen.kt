package com.example.vkt1.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.vkt1.model.Task
import com.example.vkt1.viewmodel.TaskViewModel
import java.time.LocalDate

@Composable
fun CalendarScreen(viewModel: TaskViewModel, padding: PaddingValues) {
    val tasks by viewModel.tasks.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var selectedTask by remember { mutableStateOf<Task?>(null) }

    val groupedTasks = tasks.groupBy { it.dueDate }.toSortedMap()

    Column(modifier = Modifier.padding(padding).fillMaxSize().padding(16.dp)) {
        Text("Kalenterinäkymä", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            groupedTasks.forEach { (date, tasksForDate) ->
                item {
                    Text(
                        text = if (date == LocalDate.now()) "Tänään ($date)" else date.toString(),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                items(tasksForDate) { task ->
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
    }

    if (showDialog) {
        DetailDialog(
            task = selectedTask,
            onDismiss = { showDialog = false },
            onSave = { task ->
                viewModel.updateTask(task)
                showDialog = false
            }
        )
    }
}
