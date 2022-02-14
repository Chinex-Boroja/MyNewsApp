package com.example.mynewsapp.repository.db

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mynewsapp.models.ArticleModel

interface ArticleModelDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(articleModel: ArticleModel) : Long

    @Query("SELECT * FROM articles")
    fun getArticles() : LiveData<List<ArticleModel>>

    @Delete
    suspend fun deleteArticle(articleModel: ArticleModel)
}