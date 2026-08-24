package com.example.compose.library

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compose.kanji.MainKanjiDetail

sealed class KanjiRoute(val route: String) {
    object Library : KanjiRoute("kanji_library")
    object Detail : KanjiRoute("kanji_detail/{kanji}/{mean}") {
        fun createRoute(kanji: String, mean: String) = "kanji_detail/$kanji/$mean"
    }
}

@Composable
fun KanjiFeature(modifier: Modifier = Modifier) {
    val kanjiNavController = rememberNavController()

    NavHost(
        navController = kanjiNavController,
        startDestination = KanjiRoute.Library.route,
        modifier = modifier
    ) {
        composable(KanjiRoute.Library.route) {
            MainLibrary(
                onKanjiClick = { item ->
                    kanjiNavController.navigate(
                        KanjiRoute.Detail.createRoute(item.kanji, item.mean)
                    )
                }
            )
        }
        composable(KanjiRoute.Detail.route) { backStackEntry ->
            val kanji = backStackEntry.arguments?.getString("kanji") ?: ""
            val mean = backStackEntry.arguments?.getString("mean") ?: ""
            MainKanjiDetail(
                kanji = kanji,
                mean = mean,
                onBack = { kanjiNavController.popBackStack() }
            )
        }
    }
}