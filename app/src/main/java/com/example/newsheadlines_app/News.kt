package com.example.newsheadlines_app

import com.google.gson.annotations.SerializedName

data class News (
    @SerializedName("status") var status:String?= null,
    @SerializedName("totalResults") var totalResults : Int?,
    @SerializedName("articles") var articles : ArrayList<Articles> = arrayListOf()
)