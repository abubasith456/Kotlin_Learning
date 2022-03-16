package com.example.kotlinlearning.viewModel

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.*
import com.example.kotlinlearning.db.NewsEntity
import com.example.kotlinlearning.db.RoomAppDb

class NewsDetailsViewModel(application: Application) : AndroidViewModel(application) {

     var url: String? = null
    lateinit var activity: Activity
    fun getActivity(activity: Activity, url: String?) {
        this.activity = activity
        this.url = url
    }

    fun insertNewsInfo(entity: NewsEntity) {
        val userDao = RoomAppDb.getAppDatabase(activity.applicationContext)?.userDao()
        userDao?.insertNews(entity)
    }

    fun onUrlClick(){
        val intent=Intent(Intent.ACTION_VIEW)
        intent.data= Uri.parse(url)
        activity.startActivity(intent)
    }

}