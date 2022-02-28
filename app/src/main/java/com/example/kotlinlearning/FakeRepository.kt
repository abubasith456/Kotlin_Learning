package com.example.kotlinlearning

import java.util.*

class FakeRepository {

    private val fruits: List<String> =
        listOf("Banana", "Apple", "Orange", "Mango", "PineApple", "Grapes")

    fun randomFruit(): String {
        val random = Random()
        return fruits[random.nextInt(fruits.size)]
    }
}