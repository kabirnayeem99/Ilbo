package io.github.kabirnayeem99.ilbo.repositories

import io.github.kabirnayeem99.ilbo.api.RetrofitInstance
import io.github.kabirnayeem99.ilbo.db.ArticleDatabase
import io.github.kabirnayeem99.ilbo.utils.Constants
import retrofit2.http.Query

class NewsRepository(
    val db: ArticleDatabase
) {

    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)

    suspend fun searchForNews(
        searchQuery: String,
        pageNumber: Int = 1,
        apiKey: String = Constants.API_KEY
    ) = RetrofitInstance.api.searchForNews(searchQuery, pageNumber, apiKey)
}