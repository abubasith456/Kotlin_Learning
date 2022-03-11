package com.example.kotlinlearning

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.kotlinlearning.databinding.ActivityMainBinding
import com.example.kotlinlearning.model.UserData
import com.example.kotlinlearning.viewModel.ActivityModel

var a: String = "ABU"
var b: Int = 3_00_000
val c: Int = 0
val d: Int = 80
val userData: UserData = UserData("Mohamed Abu Basith", "IdontKnown")

lateinit var activityModel: ActivityModel
lateinit var activityMainBinding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activityModel = ViewModelProviders.of(this).get(ActivityModel::class.java)
//        activityMainBinding=DataBindingUtil.setContentView(this,R.layout.activity_main)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        activityMainBinding.apply {
            this.viewModel = activityModel
            this.lifecycleOwner = this@MainActivity
        }
        activityModel.getBinding(activityMainBinding)
        activityModel.getActivity(MainActivity::class.java)

//        activityModel.text.observe(this, Observer {
//            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
//        })

//        activityMainBinding.editText.addTextChangedListener {
//            try {
//val text:String= activityMainBinding
//            } catch (e: Exception) {
//                Log.e("Error==>", e.message.toString())
//            }
//        }

//        activityMainBinding.editText.addTextChangedListener(object : TextWatcher {
//
//            override fun afterTextChanged(s: Editable) {}
//
//            override fun beforeTextChanged(
//                s: CharSequence, start: Int,
//                count: Int, after: Int
//            ) {
//            }
//
//            override fun onTextChanged(
//                s: CharSequence, start: Int,
//                before: Int, count: Int
//            ) {
//                if (s.length < 6) {
//                    Toast.makeText(applicationContext, "Failed", Toast.LENGTH_SHORT).show()
//                } else {
//                    Toast.makeText(applicationContext, "Failed", Toast.LENGTH_SHORT).show()
//                }
//            }
//        })

        moveSecondActivity()
        moveLoginActivity()
        moveNewsActivity()
    }

    private fun moveNewsActivity() {
        activityModel.moveNewsActivity.observe(this, Observer {
            if (it == true) {
                val intent = Intent(this, NewsActivity::class.java)
                startActivity(intent)
            }
        })
    }

    private fun moveLoginActivity() {
        activityModel.moveLoginActivity.observe(this, Observer {
            if (it == true) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        })
    }

    private fun moveSecondActivity() {
        activityModel.moveSecondActivity.observe(this, Observer {
            if (it == true) {
                val intent = Intent(this, SecondActivity::class.java)
                startActivity(intent)
            }
        })

        printHello("Abu")
    }

    fun printHello(name: String) {
        println("THIS IS EXAMPLE FOR KOTLIN ==> $name")

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
        var e: Int? = null
        var f = e?.dec() ?: 0
        println(f)

        var g = mutableListOf<Int>(0, 1, 2, 3, 4)
        println(g)

        println("Userdata value==> " + userData.userName)
        println("Userdata value==> " + userData.password)

        println("Function scopes------------- let, run, also, apply, with----------------->")
//        let, run, also, apply, with

        val value = "Abu upperCase"
        val upperCase = value.let { it.uppercase() }
        println(upperCase)

        val a = 10
        val b = 20
        var c = 0
        c = a.let { it + b }.let {
            val i = it + b + 10
            i
        }
        println("Double let ==> $c")

        var name: String? = "Kotlin let null check"
        name?.let { println(it) } //prints Kotlin let null check
        name = null
        name?.let { println("nothing") }

        val test = name ?: "true"

        var x = "Abu basith"
        x.let { outer -> outer.let { inner -> print("Inner is $inner and outer is $outer") } }

        var value2 = "Abu"
        value2 = run {
            val value2 = "Basith"
            value2
        }
        println(value2)

//        var userData:UserData

        var person = UserData("", "")
        var returns = UserData("", "")

        returns = with(person) {
            userName = "Basith"
            password = "password Basith"
            //That’s all for Kotlin standard functions to alter variables or modify objects within the function.
            person = UserData("Inside the with function", "Password")
            person
        }
        println(person)
        println(returns)

        person.apply {
            userName = "Abu Basith"
            password = "abubasith@"
        }
        println(person)
    }
}