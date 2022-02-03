package com.example.mynewsapp.utils

import com.example.mynewsapp.MainNews
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    companion object {
        var base_url: String = "https://newsapi.org/v2/"
    }

    @GET("top-headlines")
    fun getMainNews(@Query("country") country: String,
        @Query("apiKey") apiKey: String, @Query("pageSize") page: Int): Call<List<MainNews >>

}