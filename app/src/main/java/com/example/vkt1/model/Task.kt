package com.example.vkt1.model

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

val mockTasks = listOf(
    Task(1, "Osta maitoa", "Hae kauramaitoa kaupasta", Priority.MEDIUM, LocalDate.now().plusDays(1)),
    Task(2, "Pese pyykkiä", "Kirjopyykki 40 astetta", Priority.LOW, LocalDate.now().plusDays(2)),
    Task(3, "Tee kotitehtävät", "Android-kurssin VKT3 tehtävät", Priority.HIGH, LocalDate.now(), done = true),
    Task(4, "Käy lenkillä", "Vähintään 5km lenkki", Priority.MEDIUM, LocalDate.now().plusDays(3)),
    Task(5, "Lue kirjaa", "Lue 20 sivua uutta kirjaa", Priority.LOW, LocalDate.now().plusDays(5))
)
