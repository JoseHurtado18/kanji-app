package com.example.compose.domain.util

import com.example.compose.domain.model.KanjiReadingAPI
import org.junit.Assert.assertEquals
import org.junit.Test

class RubyParserTest {

    @Test
    fun `parse correctly extracts kanji and readings from ruby text`() {
        val rubyText = "学[がっ]校[こう]"
        val result = RubyParser.parse(rubyText)

        val expected = listOf(
            KanjiReadingAPI(kanji = "学", reading = "がっ"),
            KanjiReadingAPI(kanji = "校", reading = "こう")
        )
        assertEquals(expected, result)
    }

    @Test
    fun `parse returns empty list for non-matching text like test input`() {
        val input = "obtenerLecturasKanjisByWord"
        val result = RubyParser.parse(input)

        assertEquals(emptyList<KanjiReadingAPI>(), result)
    }
}
