package com.example.kotlinlearning.model

import android.icu.text.CaseMap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.kotlinlearning.R

class NewsHeadLines {

    var source = null
    lateinit var author: String
    lateinit var title: String
    lateinit var description: String
    lateinit var url: String
    lateinit var url_to_image: String
    lateinit var publishedAt: String
    lateinit var content: String


    fun loadImage(view: ImageView, imageUrl: String) {
        if (imageUrl != null) {
            Glide.with(view.context).load(imageUrl).into(view)
        } else {
            Glide.with(view.context)
                .load(view.resources.getDrawable(R.drawable.ic_launcher_background))
                .into(view);
        }
    }
}