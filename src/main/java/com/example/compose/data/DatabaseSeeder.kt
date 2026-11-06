package com.example.compose.data

import com.example.compose.core.database.AppDatabase
import com.example.compose.data.entity.KanjiEntity
import com.example.compose.data.entity.WordEntity
import com.example.compose.data.entity.WordLearningStateEntity
import com.example.compose.domain.model.TypeWord

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalTime
import javax.inject.Inject

data class SeedWord(
    val kanjiWord: String,
    val reading: String,
    val meaning: String,
    val typeWord: String? = null
)

class DatabaseSeeder @Inject constructor(
    private val db: AppDatabase
) {
    suspend fun seedDatabase() = withContext(Dispatchers.IO) {
        val kanjiDao = db.getKanjiDAO()
        val wordDao = db.getWordDAO()
        val stateDao = db.getWordLearningStateDao()

        // Kanjis
        val kanjis = listOf(
            KanjiEntity(character = "学", meaningEs = "Estudio", onYomi = "ガク", kunYomi = "まな.ぶ", jlptLevel = "N5", strokeCount = 8, radical = "子", mnemonic = "Un niño estudiando en la escuela.", notes = "", dateAdded = LocalTime.now()),
            KanjiEntity(character = "生", meaningEs = "Vida / Nacer", onYomi = "セイ / ショウ", kunYomi = "い.きる / う.まれる", jlptLevel = "N5", strokeCount = 5, radical = "生", mnemonic = "Una planta creciendo del suelo.", notes = "", dateAdded = LocalTime.now()),
            KanjiEntity(character = "校", meaningEs = "Escuela", onYomi = "コウ", kunYomi = "", jlptLevel = "N5", strokeCount = 10, radical = "木", mnemonic = "Un árbol cerca de un edificio escolar.", notes = "", dateAdded = LocalTime.now()),
            KanjiEntity(character = "日", meaningEs = "Sol / Día", onYomi = "ニチ / ジツ", kunYomi = "ひ / -か", jlptLevel = "N5", strokeCount = 4, radical = "日", mnemonic = "La forma del sol brillando.", notes = "", dateAdded = LocalTime.now()),
            KanjiEntity(character = "本", meaningEs = "Libro / Origen", onYomi = "ホン", kunYomi = "もと", jlptLevel = "N5", strokeCount = 5, radical = "木", mnemonic = "La raíz de un árbol marcada con una línea.", notes = "", dateAdded = LocalTime.now()),
            KanjiEntity(character = "人", meaningEs = "Persona", onYomi = "ジン / ニン", kunYomi = "ひと", jlptLevel = "N5", strokeCount = 2, radical = "人", mnemonic = "Una persona caminando con dos piernas.", notes = "", dateAdded = LocalTime.now()),
            KanjiEntity(character = "大", meaningEs = "Grande", onYomi = "ダイ / タイ", kunYomi = "おお.きい", jlptLevel = "N5", strokeCount = 3, radical = "大", mnemonic = "Una persona con los brazos estirados.", notes = "", dateAdded = LocalTime.now()),
            KanjiEntity(character = "小", meaningEs = "Pequeño", onYomi = "ショウ", kunYomi = "ちい.さい", jlptLevel = "N5", strokeCount = 3, radical = "小", mnemonic = "Una línea dividida en pedazos pequeños.", notes = "", dateAdded = LocalTime.now()),
            KanjiEntity(character = "中", meaningEs = "Medio / Dentro", onYomi = "チュウ", kunYomi = "なか", jlptLevel = "N5", strokeCount = 4, radical = "丨", mnemonic = "Una línea que atraviesa el centro de un cuadrado.", notes = "", dateAdded = LocalTime.now()),
            KanjiEntity(character = "国", meaningEs = "País", onYomi = "コク", kunYomi = "くに", jlptLevel = "N5", strokeCount = 8, radical = "囗", mnemonic = "Un rey (王) protegiendo su territorio dentro de unas murallas (囗).", notes = "", dateAdded = LocalTime.now()),
            KanjiEntity(character = "見", meaningEs = "Ver", onYomi = "ケン", kunYomi = "み.る", jlptLevel = "N5", strokeCount = 7, radical = "見", mnemonic = "Un ojo grande (目) sobre unas piernas, observando.", notes = "", dateAdded = LocalTime.now()),
            KanjiEntity(character = "行", meaningEs = "Ir", onYomi = "コウ / ギョウ", kunYomi = "い.く / ゆ.く", jlptLevel = "N5", strokeCount = 6, radical = "行", mnemonic = "Un cruce de caminos.", notes = "", dateAdded = LocalTime.now()),
            KanjiEntity(character = "食", meaningEs = "Comer", onYomi = "ショク / ジキ", kunYomi = "た.べる", jlptLevel = "N5", strokeCount = 9, radical = "食", mnemonic = "Alguien llevando comida a su boca bajo un techo.", notes = "", dateAdded = LocalTime.now()),
            KanjiEntity(character = "男", meaningEs = "Hombre", onYomi = "ダン / ナン", kunYomi = "おとこ", jlptLevel = "N5", strokeCount = 7, radical = "田", mnemonic = "Poder (力) para trabajar en el campo de arroz (田).", notes = "", dateAdded = LocalTime.now()),
            KanjiEntity(character = "女", meaningEs = "Mujer", onYomi = "ジョ / ニョ", kunYomi = "おんな", jlptLevel = "N5", strokeCount = 3, radical = "女", mnemonic = "Una mujer con los brazos cruzados sentada de rodillas.", notes = "", dateAdded = LocalTime.now()),
            KanjiEntity(character = "先", meaningEs = "Antes / Previo", onYomi = "セン", kunYomi = "さき", jlptLevel = "N5", strokeCount = 6, radical = "儿", mnemonic = "Alguien que va delante.", notes = "", dateAdded = LocalTime.now())
        )

        val wordsData = listOf(
            SeedWord("学生", "がくせい", "Estudiante", TypeWord.SUSTANTIVO.name),
            SeedWord("学校", "がっこう", "Escuela", TypeWord.SUSTANTIVO.name),
            SeedWord("日本", "にほん", "Japón", TypeWord.SUSTANTIVO.name),
            SeedWord("日本人", "にほんじん", "Persona japonesa", TypeWord.SUSTANTIVO.name),
            SeedWord("大人", "おとな", "Adulto", TypeWord.SUSTANTIVO.name),
            SeedWord("小人", "こびと", "Enano / Niño", TypeWord.SUSTANTIVO.name),
            SeedWord("中国", "ちゅうごく", "China", TypeWord.SUSTANTIVO.name),
            SeedWord("中国人", "ちゅうごくじん", "Persona china", TypeWord.SUSTANTIVO.name),
            SeedWord("本", "ほん", "Libro", TypeWord.SUSTANTIVO.name),
            SeedWord("人", "ひと", "Persona", TypeWord.SUSTANTIVO.name),
            SeedWord("見る", "みる", "Ver", TypeWord.VERBO.name),
            SeedWord("行く", "いく", "Ir", TypeWord.VERBO.name),
            SeedWord("食べる", "たべる", "Comer", TypeWord.VERBO.name),
            SeedWord("男女", "だんじょ", "Hombres y mujeres", TypeWord.SUSTANTIVO.name),
            SeedWord("男の人", "おとconotひと".replace("conot", "ko"), "Hombre", TypeWord.SUSTANTIVO.name),
            SeedWord("女の人", "おんなのひと", "Mujer", TypeWord.SUSTANTIVO.name),
            SeedWord("大学生", "だいがくせい", "Estudiante universitario", TypeWord.SUSTANTIVO.name),
            SeedWord("小学校", "しょうがっこう", "Escuela primaria", TypeWord.SUSTANTIVO.name),
            SeedWord("中学校", "ちゅうがっこう", "Escuela secundaria", TypeWord.SUSTANTIVO.name),
            SeedWord("生きる", "いきる", "Vivir", TypeWord.VERBO.name),
            SeedWord("先生", "せんせい", "profesor/a", TypeWord.SUSTANTIVO.name),
            SeedWord("大きい", "おおきい", "Grande", TypeWord.ADJETIVO.name),
            SeedWord("小さい", "ちいさい", "Pequeño", TypeWord.ADJETIVO.name)
        )

        val dbSql = db.openHelper.writableDatabase
        dbSql.beginTransaction()
        try {
            // Verificamos si al menos el primer kanji ya existe para no duplicar los datos semilla
            val cursor = dbSql.query("SELECT COUNT(*) FROM kanji_table WHERE character = '学'")
            cursor.moveToFirst()
            val count = cursor.getInt(0)
            cursor.close()

            if (count > 0) {
                // Actualizamos type_word de las palabras existentes si está nulo o vacío
                for (item in wordsData) {
                    if (item.typeWord != null) {
                        dbSql.execSQL(
                            "UPDATE word_table SET type_word = ? WHERE kanji_word = ? AND (type_word IS NULL OR type_word = '')",
                            arrayOf(item.typeWord, item.kanjiWord)
                        )
                    }
                }
                // Aseguramos que '食べる' tenga estado de repaso pendiente para la demostración
                val taberuCursor = dbSql.query("SELECT id FROM word_table WHERE kanji_word = '食べる'")
                if (taberuCursor.moveToFirst()) {
                    val taberuId = taberuCursor.getInt(0)
                    dbSql.execSQL(
                        "INSERT OR REPLACE INTO word_learning_state (wordId, status, repetitions, intervalDays, easeFactor, lastReviewedAt, nextReviewAt) VALUES (?, 'LEARNING', 1, 1, 2.5, ?, ?)",
                        arrayOf<Any>(taberuId, System.currentTimeMillis() - 86400000L, System.currentTimeMillis() - 1000L)
                    )
                }
                taberuCursor.close()

                dbSql.setTransactionSuccessful()
                return@withContext
            }

            val kanjiIds = mutableMapOf<String, Long>()

            for (kanji in kanjis) {
                kanjiIds[kanji.character] = kanjiDao.insertKanji(kanji)
            }

            for ((wordStr, reading, meaning, typeWord) in wordsData) {
                val wordId = wordDao.insertWord(
                    WordEntity(
                        kanjiWord = wordStr,
                        reading = reading,
                        meaning = meaning,
                        typeWord = typeWord
                    )
                )

                // Inicializamos el progreso (state) de esta palabra
                // Para "食べる", le asignamos revisión pendiente ("Repasar hoy")
                val isDue = wordStr == "食べる"
                stateDao.saveState(
                    WordLearningStateEntity(
                        wordId = wordId.toInt(),
                        status = if (isDue) "LEARNING" else "NEW",
                        repetitions = if (isDue) 1 else 0,
                        nextReviewAt = if (isDue) System.currentTimeMillis() - 1000L else null
                    )
                )

                // Relacionamos la palabra con cada Kanji que la compone
                val charsInWord = wordStr.map { it.toString() }
                for (char in charsInWord) {
                    val kId = kanjiIds[char]
                    if (kId != null) {
                        dbSql.execSQL(
                            "INSERT OR IGNORE INTO kanji_word_cross_ref (kanjiId, wordId) VALUES (?, ?)",
                            arrayOf(kId, wordId)
                        )
                    }
                }
            }
            dbSql.setTransactionSuccessful()
        } finally {
            dbSql.endTransaction()
        }
    }
}
