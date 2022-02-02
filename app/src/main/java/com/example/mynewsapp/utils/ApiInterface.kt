package com.example.mynewsapp.utils

import retrofit2.http.GET

interface ApiInterface {

    var base_url: String = "https://newsapi.org/v2/"

    @GET("top-headlines")
    Call<MainNews> getNews(

    )

}