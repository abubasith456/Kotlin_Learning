package com.example.kotlinlearning.viewModel

import android.app.Application
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.kotlinlearning.databinding.ForgotPasswordFragmentBinding
import com.example.kotlinlearning.utils.Utils
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordViewModel(application: Application) : AndroidViewModel(application) {


    var forgotPassword = ObservableField<String?>()
    var errorPassword = ObservableField<String>()
    var processBarVisibility = ObservableField<Boolean>()
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    lateinit var binding: ForgotPasswordFragmentBinding
    var email: String? = ""
    private val utils = Utils()

    fun getBinding(binding: ForgotPasswordFragmentBinding, email: String?) {
        this.binding = binding
        this.email = email

        if (email.equals("null")) {
            forgotPassword.set("")
        } else {
            forgotPassword.set(email)
        }

    }

    fun onForgotClick() {
        try {
            val email: String = forgotPassword.get().toString()
            if (utils.isNetworkConnectionAvailable(getApplication())) {
                if (isValidate()) {
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(
                                getApplication(),
                                "Reset mail sent to your mail!",
                                Toast.LENGTH_SHORT
                            ).show()
                            forgotPassword.set("")
                        } else {
                            Toast.makeText(
                                getApplication(),
                                "" + it.exception?.message.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            } else {
                Toast.makeText(
                    getApplication(),
                    "Please check your internet connection",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } catch (e: Exception) {
            Log.e("Error=>", e.message.toString())
        }
    }

    private fun isValidate(): Boolean {
        var valid = true
        if (forgotPassword.get() == null || forgotPassword.get()!!.isEmpty()) {
            errorPassword.set("please enter the email")
            valid = false
        }
        if (!isEmailValid(forgotPassword.get())) {
            errorPassword.set("Please enter the valid email.")
            valid = false
        }
        return valid
    }

    private fun isEmailValid(value: String?): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(value!!).matches()
    }
}