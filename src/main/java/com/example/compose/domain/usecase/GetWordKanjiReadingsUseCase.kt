package com.example.compose.domain.usecase

import com.example.compose.domain.model.KanjiReadingAPI
import com.example.compose.domain.repository.DictionaryRepository
import com.example.compose.domain.util.RubyParser
import javax.inject.Inject

class GetWordKanjiReadingsUseCase @Inject constructor(
    private val repository: DictionaryRepository
) {
    suspend operator fun invoke(word: String): Result<List<KanjiReadingAPI>> =
        runCatching {
            val results = repository.searchWords(word.trim())

            // Prefiere la coincidencia exacta; si no hay, usa el primer resultado
            val match = results.firstOrNull { it.text == word.trim() }
                ?: results.firstOrNull()

            match?.let { RubyParser.parse(it.rubyText) } ?: emptyList()
        }
}