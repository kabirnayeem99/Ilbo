package io.github.kabirnayeem99.ilbo.models

import com.google.gson.annotations.SerializedName


data class NewsResponse(

    @SerializedName("status") val status: String,
    @SerializedName("totalResults") val totalResults: Int,
    @SerializedName("articles") val articles: List<Article>
)