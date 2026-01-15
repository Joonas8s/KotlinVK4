# VKT1 - Tehtävälista (Compose)

Yksinkertainen tehtävienhallintasovellus Jetpack Composella.

### Datamalli (domain/Task.kt)
- **Task**: id, title, description, priority, dueDate, done.
- **Priority**: LOW, MEDIUM, HIGH.

### Toiminnot (Kotlin)
- `addTask`: Lisää uuden tehtävän listaan.
- `toggleDone`: Vaihtaa tehtävän suoritustilaa.
- `filterByDone`: Suodattaa tehtävät tilan perusteella.
- `sortByDueDate`: Järjestää tehtävät päivämäärän mukaan.

### UI (ui/HomeScreen.kt)
- Toteutettu täysin Composella (`Column`, `Row`, `LazyColumn`).
- Sisältää napit toimintojen käyttöön ja listan tehtävistä.
