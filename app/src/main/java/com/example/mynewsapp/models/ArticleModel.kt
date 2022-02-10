package com.example.mynewsapp.models

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ArticleModel(
    @PrimaryKey(autoGenerate = true)
    var id : Int? = null,
    @SerializedName("author")
    var author: String?,
    @SerializedName("content")
    var content: String?,
    @SerializedName("description")
    var description: String?,
    @SerializedName("publishedAt")
    var publishedAt: String,
    @SerializedName("source")
    val source: SourceStream?,
    @SerializedName("title")
    var title: String?,
    @SerializedName("urlToImage")
    val urlToImage: String?,
) : Serializable {
}
