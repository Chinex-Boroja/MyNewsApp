package com.example.mynewsapp.repository.datastream

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.bumptech.glide.load.engine.Resource
import com.example.mynewsapp.models.ArticleModel
import com.example.mynewsapp.models.MainNews
import com.example.mynewsapp.repository.service.RetrofitClient
import com.example.mynewsapp.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.lang.Exception

class ArticleDatasource(val source: CoroutineScope) : PageKeyedDataSource<Int, ArticleModel>() {

    val breakingNews: MutableLiveData<MutableList<ArticleModel>> = MutableLiveData()
    var breakingPageNumber = 1
    var breakingNewsResponse : MainNews? = null

    val searchNews: MutableLiveData<Resource<MainNews>> = MutableLiveData()
    var searchPageNumber = 1
    var seNewsResponse : MainNews? = null

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ArticleModel>
    ) {
        source.launch {
            try {
                val response = RetrofitClient.api.getBreakingNews("ng", 1, Constants.API_KEY)
                when{
                    response.isSuccessful -> {
                        response.body()?.articles?.let {
                            breakingNews.postValue(it)
                            callback.onResult(it, null, 2)
                        }
                    }
                }

            }catch (exception: Exception){
                Log.e("DataSource::", exception.message.toString())
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ArticleModel>) {
        TODO("Not yet implemented")
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ArticleModel>) {
        try {
            source.launch {
                val response = RetrofitClient.api.getBreakingNews("ng", params.requestedLoadSize, Constants.API_KEY)
                when{
                    response.isSuccessful -> {
                        response.body()?.articles?.let {
                            breakingNews.postValue(it)
                            callback.onResult(it, params.key + 1)
                        }
                    }
                }
            }
        }catch (exception: Exception){
            Log.e("DataSource::", exception.message.toString())
        }
    }
}