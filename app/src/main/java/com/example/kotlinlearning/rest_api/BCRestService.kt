package com.example.kotlinlearning.rest

import com.example.kotlinlearning.model.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface BCRestService {

    @GET("top-headlines")
    fun getNewsData(
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("q") query: String?,
        @Query("apiKey") api_key: String
    ): Call<NewsResponse>

}