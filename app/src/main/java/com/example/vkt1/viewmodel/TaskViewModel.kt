package com.example.vkt1.viewmodel

import androidx.lifecycle.ViewModel
import com.example.vkt1.model.Task
import com.example.vkt1.model.mockTasks
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TaskViewModel : ViewModel() {
    private val _tasks = MutableStateFlow<List<Task>>(mockTasks)
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    fun addTask(task: Task) {
        _tasks.update { it + task }
    }

    fun toggleDone(id: Int) {
        _tasks.update { list ->
            list.map { if (it.id == id) it.copy(done = !it.done) else it }
        }
    }

    fun removeTask(id: Int) {
        _tasks.update { list ->
            list.filter { it.id != id }
        }
    }

    fun updateTask(updatedTask: Task) {
        _tasks.update { list ->
            list.map { if (it.id == updatedTask.id) updatedTask else it }
        }
    }
}
