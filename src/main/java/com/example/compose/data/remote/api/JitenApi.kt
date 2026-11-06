package com.example.compose.data.remote.api

import com.example.compose.data.remote.dto.DictionarySearchResponse
import com.example.compose.data.remote.dto.KanjiComponentDto
import com.example.compose.data.remote.dto.WordDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JitenApi {

    @GET("api/vocabulary/search")
    suspend fun searchVocabulary(
        @Query("query") query: String
    ): DictionarySearchResponse

    @GET("api/vocabulary/{wordId}/{readingIndex}")
    suspend fun getWord(
        @Path("wordId") wordId: Int,
        @Path("readingIndex") readingIndex: Int
    ): WordDto

    @GET("api/vocabulary/{wordId}/{readingIndex}/kanji")
    suspend fun getKanjiBreakdown(
        @Path("wordId") wordId: Int,
        @Path("readingIndex") readingIndex: Int
    ): List<KanjiComponentDto>
}