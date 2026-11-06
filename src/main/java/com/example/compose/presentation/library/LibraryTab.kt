package com.example.compose.presentation.library

import androidx.annotation.StringRes
import com.example.compose.R

enum class LibraryTab(@StringRes val labelRes: Int) {
    KANJI(R.string.library_tab_kanji),
    WORDS(R.string.library_tab_words)
}