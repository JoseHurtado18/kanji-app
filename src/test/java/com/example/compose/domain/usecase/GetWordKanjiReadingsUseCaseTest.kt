package com.example.compose.domain.usecase

import com.example.compose.domain.model.KanjiReadingAPI
import com.example.compose.domain.model.WordAPI
import com.example.compose.domain.repository.DictionaryRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class FakeDictionaryRepository : DictionaryRepository {
    override suspend fun searchWords(query: String): List<WordAPI> {
        return when (query) {
            "学校" -> listOf(
                WordAPI(
                    wordId = 1,
                    readingIndex = 0,
                    text = "学校",
                    rubyText = "学[がっ]校[こう]"
                )
            )
            "obtenerLecturasKanjisByWord" -> emptyList()
            else -> emptyList()
        }
    }
}

class GetWordKanjiReadingsUseCaseTest {

    private val repository = FakeDictionaryRepository()
    private val useCase = GetWordKanjiReadingsUseCase(repository)

    @Test
    fun `when searching valid kanji word then returns parsed readings`() = runBlocking {
        val result = useCase("学校")

        assertTrue(result.isSuccess)
        val expected = listOf(
            KanjiReadingAPI(kanji = "学", reading = "がっ"),
            KanjiReadingAPI(kanji = "校", reading = "こう")
        )
        assertEquals(expected, result.getOrNull())
    }

    @Test
    fun `when searching test string obtention function name then returns empty list`() = runBlocking {
        val result = useCase("obtenerLecturasKanjisByWord")

        assertTrue(result.isSuccess)
        assertEquals(emptyList<KanjiReadingAPI>(), result.getOrNull())
    }
}
