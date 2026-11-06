package com.example.compose.domain

import com.example.compose.presentation.library.kanjiLibrary.KanjiItem

data class KanjiItem(val kanji: String, val mean: String)


val kanjisList = listOf(
    KanjiItem("水", "agua"),
    KanjiItem("学", "estudio"),
    KanjiItem("木", "árbol"),
    KanjiItem("水", "agua"),
    KanjiItem("学", "estudio"),
    KanjiItem("木", "árbol"),
    KanjiItem("水", "agua"),
    KanjiItem("学", "estudio"),
    KanjiItem("木", "árbol")
)