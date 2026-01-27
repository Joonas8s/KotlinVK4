package com.example.vkt1.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.vkt1.domain.Priority
import com.example.vkt1.domain.Task
import com.example.vkt1.domain.mockTasks
import java.time.LocalDate

class TaskViewModel : ViewModel() {
    var tasks by mutableStateOf(listOf<Task>())
        private set

    init {
        tasks = mockTasks
    }

    fun addTask(title: String, description: String, priority: Priority, dueDate: LocalDate) {
        val newTask = Task(
            id = (tasks.maxOfOrNull { it.id } ?: 0) + 1,
            title = title,
            description = description,
            priority = priority,
            dueDate = dueDate
        )
        tasks = tasks + newTask
    }

    fun toggleDone(id: Int) {
        tasks = tasks.map {
            if (it.id == id) it.copy(done = !it.done) else it
        }
    }

    fun removeTask(id: Int) {
        tasks = tasks.filter { it.id != id }
    }

    fun sortByDueDate() {
        tasks = tasks.sortedBy { it.dueDate }
    }

    fun getFilteredTasks(done: Boolean?): List<Task> {
        return when (done) {
            null -> tasks
            else -> tasks.filter { it.done == done }
        }
    }
}
