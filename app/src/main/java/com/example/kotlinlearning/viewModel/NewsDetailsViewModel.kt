package com.example.kotlinlearning.viewModel

import android.app.Activity
import android.app.Application
import androidx.lifecycle.*
import com.example.kotlinlearning.db.NewsEntity
import com.example.kotlinlearning.db.RoomAppDb

class NewsDetailsViewModel(application: Application) : AndroidViewModel(application) {


    lateinit var activity: Activity
    fun getActivity(activity: Activity) {
        this.activity = activity
    }

    fun insertNewsInfo(entity: NewsEntity) {
        val userDao = RoomAppDb.getAppDatabase(activity.applicationContext)?.userDao()
        userDao?.insertNews(entity)
    }

}