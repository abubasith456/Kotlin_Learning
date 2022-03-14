package com.example.kotlinlearning.viewModel

import android.app.Activity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import com.example.kotlinlearning.db.AppDatabase
import com.example.kotlinlearning.db.News

class NewsDetailsViewModel : ViewModel() {

    lateinit var activity: Activity

    fun getActivity(activity: Activity?) {
        if (activity != null) {
            this.activity = activity
        }
    }

    fun insertToDB(
        title: String?,
        description: String?,
        urlToImage: String?,
        author: String?,
        publishedAt: String?
    ) {

        val db: AppDatabase? = null
        db?.getDbInstance(activity)
        val news: News? = null
        news?.title = title
        news?.description = description
        news?.imageUrl = urlToImage
        news?.author = author
        news?.publishedAt = publishedAt
        if (news != null) {
            db?.userDao()?.insertNews(news)
        }

    }

}