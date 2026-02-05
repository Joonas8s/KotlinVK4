package com.example.vkt1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.vkt1.model.Task
import com.example.vkt1.ui.theme.VKT1Theme
import com.example.vkt1.view.CalendarScreen
import com.example.vkt1.view.DetailDialog
import com.example.vkt1.view.HomeScreenContent
import com.example.vkt1.view.SettingsScreen
import com.example.vkt1.viewmodel.TaskViewModel

const val ROUTE_HOME = "home"
const val ROUTE_CALENDAR = "calendar"
const val ROUTE_SETTINGS = "settings"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var isDarkTheme by remember { mutableStateOf(false) }
            VKT1Theme(darkTheme = isDarkTheme) {
                MainApp(isDarkTheme = isDarkTheme, onThemeChange = { isDarkTheme = it })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp(
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit,
    viewModel: TaskViewModel = viewModel()
) {
    val navController = rememberNavController()
    val tasks by viewModel.tasks.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Task Manager") },
                actions = {
                    IconButton(onClick = { navController.navigate(ROUTE_HOME) }) {
                        Icon(Icons.Default.List, contentDescription = "Home")
                    }
                    IconButton(onClick = { navController.navigate(ROUTE_CALENDAR) }) {
                        Icon(Icons.Default.CalendarMonth, contentDescription = "Calendar")
                    }
                    IconButton(onClick = { navController.navigate(ROUTE_SETTINGS) }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
            )
        },
        floatingActionButton = {
            val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
            if (currentRoute == ROUTE_HOME || currentRoute == ROUTE_CALENDAR) {
                FloatingActionButton(onClick = { showAddDialog = true }) {
                    Icon(Icons.Default.Add, contentDescription = "Add Task")
                }
            }
        }
    ) { innerPadding ->
        NavHost(navController = navController, startDestination = ROUTE_HOME) {
            composable(ROUTE_HOME) {
                HomeScreenContent(viewModel = viewModel, padding = innerPadding)
            }
            composable(ROUTE_CALENDAR) {
                CalendarScreen(viewModel = viewModel, padding = innerPadding)
            }
            composable(ROUTE_SETTINGS) {
                SettingsScreen(padding = innerPadding, isDarkTheme = isDarkTheme, onThemeChange = onThemeChange)
            }
        }

        if (showAddDialog) {
            DetailDialog(
                task = null,
                onDismiss = { showAddDialog = false },
                onSave = { task ->
                    viewModel.addTask(task.copy(id = (tasks.maxOfOrNull { it.id } ?: 0) + 1))
                    showAddDialog = false
                }
            )
        }
    }
}
