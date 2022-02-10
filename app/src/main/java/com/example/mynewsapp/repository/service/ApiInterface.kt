package com.example.mynewsapp.repository.service

import com.example.mynewsapp.models.MainNews
import com.example.mynewsapp.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country") country: String = "ng",
        @Query("page") pageNumber: Int,
        @Query("apiKey") apiKey: String = Constants.API_KEY
    ): Response<MainNews>

    @GET("v2/everyhing")
    suspend fun getSearchNews(
        @Query("q") searchQuery: String,
        @Query("page") pageNumber: Int,
        @Query("apiKey") apiKey: String = Constants.API_KEY
    ): Response<MainNews>


}