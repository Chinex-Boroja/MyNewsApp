package com.example.mynewsapp.models

import com.google.gson.annotations.SerializedName

data class MainNews(
    @SerializedName("articles")
    var articles:  MutableList<ArticleModel>?,
    @SerializedName("status")
    var status: String?,
    @SerializedName("totalResults")
    var totalResults: Int?
)
