package com.example.mynewsapp.repository.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mynewsapp.models.ArticleModel

@Dao
interface ArticleModelDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(articleModel: ArticleModel) : Long

    @Query("SELECT * FROM articles")
    fun getArticles() : LiveData<List<ArticleModel>>

    @Delete
    suspend fun deleteArticle(articleModel: ArticleModel)
}