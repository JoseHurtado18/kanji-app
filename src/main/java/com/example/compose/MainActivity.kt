package com.example.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.compose.data.DatabaseSeeder
import com.example.compose.ui.theme.ComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var databaseSeeder: DatabaseSeeder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        lifecycleScope.launch {
            databaseSeeder.seedDatabase()
        }

        enableEdgeToEdge()
        setContent {
            ComposeTheme {
                NavFun()

            }
        }
        
    }
}



val roundedCornerShapeValue = 20.dp