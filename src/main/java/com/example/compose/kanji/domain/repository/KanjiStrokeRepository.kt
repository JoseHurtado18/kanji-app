package com.example.compose.kanji.domain.repository

import android.content.Context
import org.json.JSONObject

interface KanjiStrokeRepository {
    fun getStrokesForKanji(kanji: String): List<String>
}