package com.example.kotlinlearning.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "headline_news")
class NewsEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "news_id")
    var id: Int = 0

    @ColumnInfo(name = "news_title")
    var title: String? = null

    @ColumnInfo(name = "news_description")
    var description: String? = null

    @ColumnInfo(name = "news_imageUrl")
    var imageUrl: String? = null

    @ColumnInfo(name = "news_author")
    var author: String? = null

    @ColumnInfo(name = "news_publishedAt")
    var publishedAt: String? = null
}




