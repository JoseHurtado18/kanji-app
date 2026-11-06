package com.example.compose.domain.model

data class KanjiReadingAPI(
    val kanji: String,    // normalmente 1 carácter; puede ser un grupo (ej. 大人)
    val reading: String   // lectura en esta palabra (ej. がっ)
)