package com.example.kotlinlearning.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class News(
    @PrimaryKey val newsId: Int,
    @ColumnInfo(name = "news_title") var title: String?,
    @ColumnInfo(name = "news_description") var description: String?,
    @ColumnInfo(name = "news_imageUrl") var imageUrl: String?,
    @ColumnInfo(name = "news_author") var author: String?,
    @ColumnInfo(name = "news_publishedAt") var publishedAt: String?
)


