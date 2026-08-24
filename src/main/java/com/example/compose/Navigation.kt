package com.example.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.compose.home.Main
import com.example.compose.library.MainLibrary
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose.ui.theme.ComposeTheme
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.compose.library.KanjiFeature

enum class Destination(
    val route: String,
    val label: String,
    val icon: ImageVector,
    val contentDescription: String
){
    Home("home", "Home", Icons.Default.Home, "Home"),
    Library("library", "Biblioteca", Icons.Default.Favorite, "Biblioteca"),
    Stats("stats", "Estadisticas", Icons.Default.Star, "Estadisticas")
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: Destination,
    modifier: Modifier = Modifier
){
    NavHost(
        navController = navController,
        startDestination = startDestination.route,
        modifier = modifier
    ) {
        Destination.entries.forEach { destination ->
            composable(destination.route) {
                when (destination) {
                    Destination.Home -> Main()
                    Destination.Library -> KanjiFeature()
                    Destination.Stats -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("Pantalla de Estadísticas")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NavFun(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    val startDestination = Destination.Home

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        modifier = modifier,

        bottomBar = {

            NavigationBar(
                windowInsets = NavigationBarDefaults.windowInsets,
                containerColor = colorResource(R.color.kvinegro),
                contentColor = Color.Gray
            ) {

                Destination.entries.forEach { destination ->

                    val selected = currentRoute == destination.route

                    NavigationBarItem(
                        selected = selected,

                        onClick = {
                            navController.navigate(destination.route) {

                                // Evita crear múltiples copias
                                // de la misma pantalla
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }

                                // Evita múltiples copias
                                // cuando pulsas varias veces
                                launchSingleTop = true

                                // Recupera el estado anterior
                                restoreState = true
                            }
                        },

                        icon = {
                            Icon(
                                imageVector = destination.icon,
                                contentDescription = destination.contentDescription
                            )
                        },

                        label = {
                            Text(destination.label)
                        },

                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = colorResource(R.color.kvinegro),
                            selectedIconColor = colorResource(R.color.icBlue),
                            selectedTextColor = colorResource(R.color.icBlue),
                            unselectedIconColor = Color.Gray,
                            unselectedTextColor = Color.Gray
                        )
                    )
                }
            }


        }
    ) { contentPadding ->

        AppNavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(contentPadding)
        )
    }
}

@Preview
@Composable
fun NavPreview(){
    ComposeTheme {
        NavFun()
    }
}
