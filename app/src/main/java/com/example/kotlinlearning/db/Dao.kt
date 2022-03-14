package com.example.kotlinlearning.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


//Data access object (Dao)

@Dao
interface Dao {
    @Query("SELECT * FROM news")
    fun getNews(): List<News>

    @Insert
    fun insertNews(vararg news: News)

    @Delete
    fun deleteNews(news: News)
}