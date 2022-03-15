package com.example.kotlinlearning.model

import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

data class Article(
    val author: String?,
    val content: String,
    val description: String,
    val publishedAt: String?,
    val source: Source?,
    val title: String,
    val url: String?,
    val urlToImage: String?,
)