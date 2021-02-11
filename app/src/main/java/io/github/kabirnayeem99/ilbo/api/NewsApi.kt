package io.github.kabirnayeem99.ilbo.api

import io.github.kabirnayeem99.ilbo.constants.Constants
import io.github.kabirnayeem99.ilbo.models.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("/v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        countryCode: String = "us",
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = Constants.API_KEY
    ): Response<NewsResponse>

    @GET("/v2/everything")
    suspend fun searchForNews(
        @Query("q")
        searchQuery: String,
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = Constants.API_KEY
    ): Response<NewsResponse>
}