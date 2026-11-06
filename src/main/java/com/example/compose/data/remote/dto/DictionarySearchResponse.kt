package com.example.compose.data.remote.dto

import kotlinx.serialization.Serializable
// Usa @Serializable si usas kotlinx.serialization; con Moshi/Gson quítalas
@Serializable
data class DictionarySearchResponse(
    val query: String,
    val queryType: String,
    val results: List<SearchResultDto>,
    val hasMore: Boolean
    // dictionaryResults: llegó vacío, no sé su forma; se ignora
)


