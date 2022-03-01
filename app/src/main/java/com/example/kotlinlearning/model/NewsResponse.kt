package com.example.kotlinlearning.model

import com.google.gson.annotations.SerializedName

abstract class NewsResponse : List<NewsHeadLines>

//{
//    @SerializedName("status")
//    var status: String = ""
//
//    @SerializedName("totalResult")
//    var totalResult: String = ""
//
//    @SerializedName("articles")
//    lateinit var articles: List<NewsHeadLines>
//
//}