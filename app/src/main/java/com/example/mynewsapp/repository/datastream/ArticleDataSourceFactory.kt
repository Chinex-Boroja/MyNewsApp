package com.example.mynewsapp.repository.datastream

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.mynewsapp.models.ArticleModel
import kotlinx.coroutines.CoroutineScope

class ArticleDataSourceFactory(private val scope: CoroutineScope) : DataSource.Factory<Int, ArticleModel>() {

    val articleDataSourceLiveData = MutableLiveData<ArticleDatasource>()

    override fun create(): DataSource<Int, ArticleModel> {
        val newArticleDatasource = ArticleDatasource(scope)
        articleDataSourceLiveData.postValue(newArticleDatasource)
        return newArticleDatasource
    }
}