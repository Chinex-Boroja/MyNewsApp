package com.example.mynewsapp.repository

import com.example.mynewsapp.models.ArticleModel
import com.example.mynewsapp.repository.db.ArticleModelDatabase
import com.example.mynewsapp.repository.service.RetrofitClient

class NewsRepository(
    val db: ArticleModelDatabase
) {
    suspend fun getBreakingNews(countryCode : String, pageNumber : Int) =
        RetrofitClient.api.getBreakingNews(countryCode, pageNumber)

    suspend fun getSearchNews(query : String, pageNumber: Int) =
        RetrofitClient.api.getSearchNews(query, pageNumber)

    suspend fun insert(articleModel: ArticleModel) = db.getArticleDAO().insert(articleModel)
    suspend fun delete(articleModel: ArticleModel) = db.getArticleDAO().deleteArticle(articleModel)

    fun getAllArticles() = db.getArticleDAO().getArticles()
}