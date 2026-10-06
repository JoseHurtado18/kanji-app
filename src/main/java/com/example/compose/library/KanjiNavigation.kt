package com.example.compose.library

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.compose.kanji.presentation.detail.MainKanjiDetail
import androidx.compose.runtime.getValue
import com.example.compose.kanji.presentation.add.FormAddKanjiScreen
import com.example.compose.kanji.presentation.edit.EditScreen
import com.example.compose.library.presentation.MainLibrary
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.compose.runtime.collectAsState
import com.example.compose.kanji.presentation.detail.KanjiViewModel
import com.example.compose.kanji.presentation.detail.KanjiEvent

sealed class KanjiRoute(val route: String) {
    object Library : KanjiRoute("kanji_library")
    object Detail : KanjiRoute("kanji_detail/{kanji_id}") {
        fun createRoute(kanji_id: Int) = "kanji_detail/$kanji_id"
    }
    object Add : KanjiRoute("add_kanji"){
        fun createRoute() = "add_kanji"
    }
    object Edit : KanjiRoute("edit_kanji/{kanjiId}") {
        fun createRoute(kanjiId: Int) = "edit_kanji/$kanjiId"
    }
}

@Composable
fun KanjiFeature(modifier: Modifier = Modifier,
                 onDetailVisibleChange: (Boolean) -> Unit = {},
                 onAddKanjiVisibleChange: (Boolean) -> Unit = {},
                 onEditKanjiVisibleChange: (Boolean) -> Unit = {}
) {
    val kanjiNavController = rememberNavController()

    val backStackEntry by kanjiNavController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    LaunchedEffect(currentRoute) {
        onDetailVisibleChange(currentRoute == KanjiRoute.Detail.route)
        onAddKanjiVisibleChange(currentRoute == KanjiRoute.Add.route)
        onEditKanjiVisibleChange(currentRoute == KanjiRoute.Edit.route)
    }


    NavHost(
        navController = kanjiNavController,
        startDestination = KanjiRoute.Library.route,
        modifier = modifier
    ) {
        composable(KanjiRoute.Library.route) {
            MainLibrary(
                onKanjiClick = { item ->
                    kanjiNavController.navigate(
                        KanjiRoute.Detail.createRoute(item.id)
                    )
                },
                onAddKanjiClick = {
                    kanjiNavController.navigate(
                        KanjiRoute.Add.createRoute()
                    )
                },
                viewModel = hiltViewModel()
            )
        }
        composable(
            route = KanjiRoute.Detail.route,
            arguments = listOf(navArgument("kanji_id") { type = NavType.IntType })
        ) { entry ->
            val viewModel: KanjiViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()
            val kanjiId = entry.arguments?.getInt("kanji_id") ?: 0

            LaunchedEffect(kanjiId) {
                viewModel.onEvent(KanjiEvent.LoadKanjiById(kanjiId))
            }

            uiState.selectedKanji?.let { kanji ->
                MainKanjiDetail(
                    onEditClic = { kanjiToEdit ->
                        kanjiNavController.navigate(
                            KanjiRoute.Edit.createRoute(kanjiToEdit.id)
                        )
                    },
                    kanji = kanji,
                    onBack = { kanjiNavController.popBackStack() }
                )
            }
        }

        composable(KanjiRoute.Add.route) {
            FormAddKanjiScreen(
                onBack = { kanjiNavController.popBackStack() },
                viewModel = hiltViewModel()
            )
        }

        composable(
            route = KanjiRoute.Edit.route,
            arguments = listOf(navArgument("kanjiId") { type = NavType.IntType })
        ) {
            EditScreen(

                onBack = { kanjiNavController.popBackStack() },
                viewModel = hiltViewModel()
            )
        }
    }
}