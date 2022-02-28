package com.example.kotlinlearning

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ActivityModel : ViewModel() {

    var text = MutableLiveData<String>()
    var anotherText = MutableLiveData<String>()
    var textView = MutableLiveData<String>()
    fun randomFruit() = FakeRepository().randomFruit()


    fun onSubmit() {
        textView.value = text.value
    }

    fun showRandom() {
        text.value = randomFruit()
    }

    fun changeFruitName(){
        anotherText.value=randomFruit()
    }
}