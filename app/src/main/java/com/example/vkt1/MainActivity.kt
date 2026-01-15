package com.example.vkt1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.vkt1.ui.HomeScreen
import com.example.vkt1.ui.theme.VKT1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VKT1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomeScreenWrapper(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@androidx.compose.runtime.Composable
fun HomeScreenWrapper(modifier: Modifier = Modifier) {
    androidx.compose.foundation.layout.Box(modifier = modifier) {
        HomeScreen()
    }
}
