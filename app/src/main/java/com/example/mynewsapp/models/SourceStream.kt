package com.example.mynewsapp.models

import com.google.gson.annotations.SerializedName

data class SourceStream(
    @SerializedName("id")
    var id : String?,
    @SerializedName("name")
    var name: String?
)
