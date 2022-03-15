package com.example.kotlinlearning.viewModel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordViewModel(application: Application) : AndroidViewModel(application) {


    var ForgotPassword: MutableLiveData<String> = MutableLiveData()
    var ErrorPassword: MutableLiveData<String> = MutableLiveData()
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun onForgotClick() {
        try {
            val email: String = ForgotPassword.value.toString()
            if (!email.isEmpty() && !email.equals("")) {
                firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(
                            getApplication(),
                            "Reset mail sent to your mail!",
                            Toast.LENGTH_SHORT
                        ).show()
                        ForgotPassword.postValue("")
                    } else {
                        Toast.makeText(
                            getApplication(),
                            "" + it.exception.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                ErrorPassword.value = "Please enter the email"
            }
        } catch (e: Exception) {
            Log.e("Error=>", e.message.toString())
        }
    }
}