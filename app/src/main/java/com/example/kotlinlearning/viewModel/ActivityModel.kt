package com.example.kotlinlearning.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kotlinlearning.MainActivity
import com.example.kotlinlearning.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class ActivityModel(application: Application) : AndroidViewModel(application) {

    var check: MutableLiveData<Boolean> = MutableLiveData()

    lateinit var activityMainBinding: ActivityMainBinding
    lateinit var activity: Class<MainActivity>
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    fun getActivity(java: Class<MainActivity>) {
        this.activity = java
    }

    fun getBinding(activityMainBinding: ActivityMainBinding) {
        this.activityMainBinding = activityMainBinding
    }

    fun checkExistUser(): LiveData<Boolean> {

        if (firebaseAuth.currentUser != null) {
            check.postValue(true)
        } else {
            check.postValue(false)
        }
        return check
    }

}