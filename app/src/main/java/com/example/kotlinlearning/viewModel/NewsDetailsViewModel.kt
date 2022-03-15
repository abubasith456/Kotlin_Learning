package com.example.kotlinlearning.viewModel

import android.app.Activity
import android.app.Application
import android.widget.Toast
import androidx.lifecycle.*
import com.example.kotlinlearning.db.NewsEntity
import com.example.kotlinlearning.db.RoomAppDb
import com.example.kotlinlearning.repository.DBRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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