package com.example.kotlinlearning.db

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao


//Data access object (Dao)

@Dao
interface Dao {
    @Query("SELECT * FROM headline_news ORDER BY news_id ASC")
    fun getAllNews(): List<NewsEntity>?

    @Insert
    fun insertNews(news: NewsEntity?)

    @Delete
    fun deleteNews(news: NewsEntity?)

    @Update
    fun updateNews(news: NewsEntity?)
}