package com.example.kotlinlearning

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.kotlinlearning.databinding.ActivityMainBinding

var a: String = "ABU"
var b: Int = 3_00_000
val c: Int = 0
val d: Int = 80

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val viewModel = ViewModelProviders.of(this).get(ActivityModel::class.java)
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main).apply {
            this.viewModel = viewModel
            this.setLifecycleOwner(this@MainActivity)
        }

        viewModel.text.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
//        printHello()
    }

    fun printHello() {
        a = b.toString();
        println("Value is==> " + a.plus(b) + " Another value==>" + a)
        println("Value of string conversion ==> $c and $d ")

        //if statement with range checking
        if (c in 1..35) {
            println("Value C not in 1 to 35 range")
        } else if (c in 36..100) {
            println("Value C in 36 to 100 range")
        } else {
            println("Number is zero or above 100")
        }

        //Same like a switch
        when (c) {
            0 -> println("Number is zero")
            in 1..100 -> println("Number in 1 to 100")
            else -> println("More than 100")
        }

        //Nullability check "?" indicates if the variable may contain Null value also.
        var e: Int? = null;
        var f = e?.dec() ?: 0
        println(f)

        var g = mutableListOf<Int>(0, 1, 2, 3, 4)
        println(g)
    }
}