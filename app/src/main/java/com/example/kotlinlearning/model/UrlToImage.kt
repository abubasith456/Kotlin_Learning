package com.example.kotlinlearning.model

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.kotlinlearning.R

@BindingAdapter("imageUrl")
fun urlToImage(view: ImageView, imageUrl: String?) {
    if (imageUrl != null) {
        Glide.with(view.context)
            .load(imageUrl)
            .into(view)
    } else {
        Glide.with(view.context)
            .load(view.resources.getDrawable(R.drawable.ic_launcher_background))
            .into(view)
    }
}