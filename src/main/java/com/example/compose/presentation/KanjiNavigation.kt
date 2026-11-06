package com.example.compose.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.compose.runtime.collectAsState
import com.example.compose.presentation.kanji.add.FormAddKanjiScreen
import com.example.compose.presentation.kanji.detail.KanjiViewModel
import com.example.compose.presentation.kanji.detail.MainKanjiDetail
import com.example.compose.presentation.kanji.edit.EditScreen
import com.example.compose.presentation.library.LibraryScreen
import com.example.compose.presentation.word.add.AddWordScreen
import com.example.compose.presentation.word.detalle.WordScreen
import com.example.compose.presentation.word.detalle.WordViewModel
import com.example.compose.presentation.word.edit.EditWordScreen

sealed class KanjiRoute(val route: String) {
    object Library : KanjiRoute("library")
    object Detail : KanjiRoute("kanji_detail/{kanji_id}") {
        fun createRoute(kanji_id: Int) = "kanji_detail/$kanji_id"
    }

    object Detail_Word : KanjiRoute("word_detail/{word_id}"){
        fun createRoute(word_id: Int) = "word_detail/$word_id"
    }
    object Add : KanjiRoute("add_kanji"){
        fun createRoute() = "add_kanji"
    }

    object Add_Word : KanjiRoute("add_word"){
        fun createRoute() = "add_word"
    }
    object Edit : KanjiRoute("edit_kanji/{kanjiId}") {
        fun createRoute(kanjiId: Int) = "edit_kanji/$kanjiId"
    }

    object Edit_Word : KanjiRoute("edit_word/{wordId}") {
        fun createRoute(wordId: Int) = "edit_word/$wordId"
    }
}

@Composable
fun KanjiFeature(modifier: Modifier = Modifier,
                 onDetailVisibleChange: (Boolean) -> Unit = {},
                 onAddKanjiVisibleChange: (Boolean) -> Unit = {},
                 onEditKanjiVisibleChange: (Boolean) -> Unit = {},
                 onDetailWordVisibleChange: (Boolean) -> Unit = {},
                 onAddWordVisibleChange: (Boolean) -> Unit = {},
                 onEditWordVisibleChange: (Boolean) -> Unit = {}

) {
    val kanjiNavController = rememberNavController()

    val backStackEntry by kanjiNavController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    LaunchedEffect(currentRoute) {
        onDetailVisibleChange(currentRoute == KanjiRoute.Detail.route)
        onAddKanjiVisibleChange(currentRoute == KanjiRoute.Add.route)
        onEditKanjiVisibleChange(currentRoute == KanjiRoute.Edit.route)
        onDetailWordVisibleChange(currentRoute == KanjiRoute.Detail_Word.route)
        onAddWordVisibleChange(currentRoute == KanjiRoute.Add_Word.route)
        onEditWordVisibleChange(currentRoute == KanjiRoute.Edit_Word.route)
    }


    NavHost(
        navController = kanjiNavController,
        startDestination = KanjiRoute.Library.route,
        modifier = modifier
    ) {
        composable(KanjiRoute.Library.route) {
            LibraryScreen(
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
                onWordClick = { item ->
                    kanjiNavController.navigate(
                        KanjiRoute.Detail_Word.createRoute(item.id)
                    )
                },

                onAddWordClick = {
                    kanjiNavController.navigate(
                        KanjiRoute.Add_Word.createRoute()
                    )
                }
                //viewModel = hiltViewModel()
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
                viewModel.loadKanjiById(kanjiId)
            }

            uiState.selectedKanji?.let { kanji ->
                MainKanjiDetail(
                    onEditClic = { kanjiToEdit ->
                        kanjiNavController.navigate(
                            KanjiRoute.Edit.createRoute(kanjiToEdit.id)
                        )
                    },
                    kanji = kanji,
                    viewModel = hiltViewModel(),
                    onBack = { kanjiNavController.popBackStack() }
                )
            }
        }

        composable(
            route = KanjiRoute.Detail_Word.route,
            arguments = listOf(navArgument("word_id") { type = NavType.IntType })
        ) { entry ->
            val viewModel: WordViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsState()
            val word_id = entry.arguments?.getInt("word_id") ?: 0

            LaunchedEffect(word_id) {
                viewModel.loadWordById(word_id)
            }

            uiState.selectedWord?.let { word ->
                WordScreen(
                    onEditClic = { wordToEdit ->
                        kanjiNavController.navigate(
                            KanjiRoute.Edit_Word.createRoute(wordToEdit.id)
                        )
                    },
                    word = word,
                    viewModel = viewModel,
                    onBack = { kanjiNavController.popBackStack() }
                )
            }
        }

        //Add kanji vista
        composable(KanjiRoute.Add.route) {
            FormAddKanjiScreen(
                onBack = { kanjiNavController.popBackStack() },
                viewModel = hiltViewModel()
            )
        }

        //Add word Vista
        composable(KanjiRoute.Add_Word.route){
            AddWordScreen(
                onBack = { kanjiNavController.popBackStack()},
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


        composable(
            route = KanjiRoute.Edit_Word.route,
            arguments = listOf(navArgument("wordId") { type = NavType.IntType })
        ) {
            EditWordScreen(
                onBack = { kanjiNavController.popBackStack() },
                viewModel = hiltViewModel()
            )
        }
    }
}