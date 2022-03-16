package com.example.kotlinlearning.viewModel

import android.app.Activity
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kotlinlearning.db.NewsEntity
import com.example.kotlinlearning.db.RoomAppDb

class OfflineNewsViewModel(application: Application) : AndroidViewModel(application) {

    var getAllNews : MutableLiveData<List<NewsEntity>?> = MutableLiveData()

    lateinit var activity: Activity

    fun getActivity(activity: Activity) {
        this.activity = activity
    }

    fun getOfflineNewsHeadlines(): LiveData<List<NewsEntity>?>{
        try {
            val userDao = RoomAppDb.getAppDatabase((getApplication()))?.userDao()
            val list = userDao?.getAllNews()
            getAllNews.postValue(list)

        } catch (exception: Exception) {
            Log.e("Error from db==>", exception.toString())
        }
        return getAllNews
    }

}