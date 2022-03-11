package com.example.kotlinlearning.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.kotlinlearning.MainActivity
import com.example.kotlinlearning.databinding.ActivityMainBinding
import com.example.kotlinlearning.repository.FakeRepository

class ActivityModel(application: Application) : AndroidViewModel(application) {

    var text = MutableLiveData<String>()
    var anotherText = MutableLiveData<String>()
    var textView = MutableLiveData<String>()
    var moveSecondActivity = MutableLiveData<Boolean>(false)
    var moveLoginActivity = MutableLiveData<Boolean>(false)
    var moveNewsActivity = MutableLiveData<Boolean>(false)
    lateinit var activityMainBinding: ActivityMainBinding
    fun randomFruit() = FakeRepository().randomFruit()
    lateinit var activity: Class<MainActivity>

    fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
    }

    fun getActivity(java: Class<MainActivity>) {
        this.activity = java
    }

    fun getBinding(activityMainBinding: ActivityMainBinding) {
        this.activityMainBinding = activityMainBinding
    }

    fun onSubmit() {
        textView.value = text.value
    }

    fun showRandom() {
        text.value = randomFruit()
    }

    fun changeFruitName() {
        anotherText.value = randomFruit()
    }

    fun moveSecondActivity() {
        moveSecondActivity.value = true
    }

    fun moveLoginActivity() {
        moveLoginActivity.value = true
    }

    fun moveNewsActivity() {
        moveNewsActivity.value = true
    }
}