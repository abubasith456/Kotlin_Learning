package com.example.kotlinlearning.viewModel

import android.app.Application
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinlearning.databinding.ForgotPasswordFragmentBinding
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordViewModel(application: Application) : AndroidViewModel(application) {


    var ForgotPassword: MutableLiveData<String?> = MutableLiveData()
    var ErrorPassword: MutableLiveData<String> = MutableLiveData()
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    lateinit var binding: ForgotPasswordFragmentBinding

    init {
        ForgotPassword.value = null
    }

    fun getBinding(binding: ForgotPasswordFragmentBinding) {
        this.binding = binding
    }


    fun onForgotClick() {
        try {
            val email: String = ForgotPassword.value.toString()
            if (isValidate()) {
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

    fun isValidate(): Boolean {
        var valid = true
        if (ForgotPassword.value == null || ForgotPassword.value!!.isEmpty()) {
            binding.editTextEmailInput.error = "Please enter the email."
            valid = false
        }
        if (!isEmailValid(ForgotPassword.value)) {
            binding.editTextEmailInput.error = "Please enter the valid email."
            valid = false

        }
        return valid
    }

    fun isEmailValid(value: String?): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(value).matches()
    }
}