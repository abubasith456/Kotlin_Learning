package com.example.kotlinlearning.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinlearning.repository.FakeRepository
import com.example.kotlinlearning.MainActivity

class ActivityModel : ViewModel() {

    var text = MutableLiveData<String>()
    var anotherText = MutableLiveData<String>()
    var textView = MutableLiveData<String>()
    var moveSecondActivity = MutableLiveData<Boolean>(false)
    fun randomFruit() = FakeRepository().randomFruit()
    lateinit var activity: Class<MainActivity>

    fun getActivity(java: Class<MainActivity>) {
        this.activity = java
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
}