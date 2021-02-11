package io.github.kabirnayeem99.ilbo.models

import com.google.gson.annotations.SerializedName

data class Source(

    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String
)