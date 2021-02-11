package io.github.kabirnayeem99.ilbo.api

import androidx.lifecycle.LiveData
import androidx.room.*
import io.github.kabirnayeem99.ilbo.models.Article

@Dao
interface ArticlesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Article): Long

    @Query("SELECT * FROM articles")
    fun getAllNews(): LiveData<List<Article>>

    @Delete
    suspend fun delete(article: Article)
}