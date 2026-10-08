package com.example.compose.kanji.data.repository

import android.content.Context
import com.example.compose.kanji.domain.repository.KanjiStrokeRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import org.json.JSONObject
import javax.inject.Inject

class KanjiStrokeRepositoryImpl @Inject constructor(
    @ApplicationContext
    private val context: Context
): KanjiStrokeRepository {

    // Mapa que relaciona el Kanji con su lista de trazos
    private val strokeMap = mutableMapOf<String, List<String>>()

    init {
        loadStrokesFromAssets()
    }

    private fun loadStrokesFromAssets() {
        try {
            // Leer el archivo JSON desde la carpeta assets
            val jsonString = context.assets.open("kanji_strokes.json")
                .bufferedReader()
                .use { it.readText() }

            val jsonObject = JSONObject(jsonString)

            // Llenar el mapa
            jsonObject.keys().forEach { kanji ->
                val jsonArray = jsonObject.getJSONArray(kanji)
                val strokes = mutableListOf<String>()
                for (i in 0 until jsonArray.length()) {
                    strokes.add(jsonArray.getString(i))
                }
                strokeMap[kanji] = strokes
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Función para obtener los trazos de un kanji específico
    override  fun getStrokesForKanji(kanji: String): List<String> {
        return strokeMap[kanji] ?: emptyList()
    }
}