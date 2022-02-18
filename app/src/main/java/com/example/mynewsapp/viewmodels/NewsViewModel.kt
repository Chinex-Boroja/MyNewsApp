package com.example.mynewsapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList

import com.example.mynewsapp.models.ArticleModel
import com.example.mynewsapp.models.MainNews
import com.example.mynewsapp.repository.NewsRepository
import com.example.mynewsapp.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    val newsRepository: NewsRepository
): ViewModel() {
    val breakingNews: MutableLiveData<Resource<MainNews>> = MutableLiveData()
    var breakingPageNumber = 1
    var breakingNewsResponse : MainNews? = null

    val searchNews: MutableLiveData<Resource<MainNews>> = MutableLiveData()
    var searchPageNumber = 1
    var searchNewsResponse : MainNews? = null

    lateinit var articles : LiveData<PagedList<ArticleModel>>
    init {
        getBreakingNews("ng")
    }

    private fun getBreakingNews(countryCode: String) = viewModelScope.launch{
        breakingNews.postValue(Resource.Loading())
        val response = newsRepository.getBreakingNews(countryCode, breakingPageNumber)
        breakingNews.postValue(handleBreakNewsResponse(response))

    }

    private fun handleBreakNewsResponse(response: Response<MainNews>): Resource<MainNews>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                breakingPageNumber++
                if (breakingNewsResponse == null) {
                    breakingNewsResponse == resultResponse
                }else{
                    val oldArticles = breakingNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    if (newArticles != null) {
                        oldArticles?.addAll(newArticles)
                    }
                }
                return Resource.Success(breakingNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun getSearchedNews(queryString: String) = viewModelScope.launch {
        searchNews.postValue(Resource.Loading())
        val searchNewsResponse = newsRepository.getSearchNews(queryString, searchPageNumber)
        searchNews.postValue(handleSearchNewsResponse(searchNewsResponse))
    }

    private fun handleSearchNewsResponse(response: Response<MainNews>): Resource<MainNews>? {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                searchPageNumber++
                if (searchNewsResponse == null) {
                    searchNewsResponse = resultResponse
                }else{
                    val oldArticles = breakingNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    if (newArticles != null) {
                        oldArticles?.addAll(newArticles)
                    }
                }
                return Resource.Success(searchNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun insertArticle(articleModel: ArticleModel) = viewModelScope.launch {
        newsRepository.insert(articleModel)
    }

    fun deleteArticle(articleModel: ArticleModel) = viewModelScope.launch {
        newsRepository.delete(articleModel)
    }
    fun getSavedArticles() = newsRepository.getAllArticles()

    fun getBreakingNews() : LiveData<PagedList<ArticleModel>> {
        return articles
    }
}