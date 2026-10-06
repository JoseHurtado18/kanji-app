package com.example.compose

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.compose.home.Main
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose.ui.theme.ComposeTheme
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.compose.library.KanjiFeature
import com.example.compose.stats.StatsScreen

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
    onKanjiDetailVisibleChange: (Boolean) -> Unit,
    onAddKanjiVisibleChange: (Boolean) -> Unit,
    onEditKanjiVisibleChange: (Boolean) -> Unit,
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
                    Destination.Library -> KanjiFeature(
                        onDetailVisibleChange = onKanjiDetailVisibleChange,
                        onAddKanjiVisibleChange = onAddKanjiVisibleChange,
                        onEditKanjiVisibleChange = onEditKanjiVisibleChange
                    )
                    Destination.Stats -> StatsScreen()
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

    var isKanjiDetailVisible by rememberSaveable { mutableStateOf(false) }
    var isAddKanjiVisible by rememberSaveable { mutableStateOf(false)}
    var isEditKanjiVisible by rememberSaveable { mutableStateOf(false) }

    val bottomBarRoutes = remember { Destination.entries.map { it.route }.toSet() }
    val showBottomBar = (currentRoute in bottomBarRoutes) && !(isKanjiDetailVisible || isAddKanjiVisible || isEditKanjiVisible)
    //val showFloatingButton = currentRoute == Destination.Library.route && !isKanjiDetailVisible


    Scaffold(
        modifier = modifier,

        bottomBar = {
            if (showBottomBar){
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
            }}

        }
    ) { contentPadding ->

        AppNavHost(
            navController = navController,
            startDestination = startDestination,
            onKanjiDetailVisibleChange = { isKanjiDetailVisible = it },
            onAddKanjiVisibleChange = {isAddKanjiVisible = it},
            onEditKanjiVisibleChange = { isEditKanjiVisible = it },
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
