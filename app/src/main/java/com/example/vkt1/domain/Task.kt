package com.example.vkt1.domain

import java.time.LocalDate

enum class Priority {
    LOW, MEDIUM, HIGH
}

data class Task(
    val id: Int,
    val title: String,
    val description: String,
    val priority: Priority,
    val dueDate: LocalDate,
    val done: Boolean = false
)

// Mock-data (5-10 kpl)
val mockTasks = listOf(
    Task(1, "Osta maitoa", "Hae kauramaitoa kaupasta", Priority.MEDIUM, LocalDate.now().plusDays(1)),
    Task(2, "Pese pyykkiä", "Kirjopyykki 40 astetta", Priority.LOW, LocalDate.now().plusDays(2)),
    Task(3, "Tee kotitehtävät", "Android-kurssin VKT1 tehtävät", Priority.HIGH, LocalDate.now(), done = true),
    Task(4, "Käy lenkillä", "Vähintään 5km lenkki", Priority.MEDIUM, LocalDate.now().plusDays(3)),
    Task(5, "Lue kirjaa", "Lue 20 sivua uutta kirjaa", Priority.LOW, LocalDate.now().plusDays(5)),
    Task(6, "Siivoa keittiö", "Tiskaa ja pyyhi tasot", Priority.MEDIUM, LocalDate.now().minusDays(1)),
    Task(7, "Maksa laskut", "Vuokra ja sähkölasku", Priority.HIGH, LocalDate.now().plusDays(4))
)

// Kotlin-funktiot listan käsittelyyn
fun addTask(list: List<Task>, task: Task): List<Task> {
    return list + task
}

fun toggleDone(list: List<Task>, id: Int): List<Task> {
    return list.map {
        if (it.id == id) it.copy(done = !it.done) else it
    }
}

fun filterByDone(list: List<Task>, done: Boolean): List<Task> {
    return list.filter { it.done == done }
}

fun sortByDueDate(list: List<Task>): List<Task> {
    return list.sortedBy { it.dueDate }
}
