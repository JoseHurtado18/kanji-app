package com.example.compose.domain.util

import com.example.compose.domain.model.KanjiReadingAPI


object RubyParser {
    private val RUBY = Regex("""([\p{IsHan}々]+)\[([^\]]+)]""")

    fun parse(rubyText: String): List<KanjiReadingAPI> =
        RUBY.findAll(rubyText)
            .map { KanjiReadingAPI(kanji = it.groupValues[1], reading = it.groupValues[2]) }
            .toList()
}