package com.example.kotlinlearning.viewModel

import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinlearning.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivityViewModel : ViewModel() {

    var emailText = MutableLiveData<String>("")
    var passwordText = MutableLiveData<String>("")
    var emailTextHelper = MutableLiveData<String>("*Required")
    var passwordTextHelper = MutableLiveData<String>("*Required")
    lateinit var loginActivity: Class<LoginActivity>

    var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()


    fun getActivity(loginActivity: Class<LoginActivity>) {
        this.loginActivity = loginActivity
    }

    fun emailOnFocused(value: Boolean) {
        if (!value) {
            emailTextHelper.value = emailValid()
        }
    }

    fun passwordOnFocused(value: Boolean) {
        if (!value) {
            passwordTextHelper.value = validPassword()
        }
    }

    private fun emailValid(): String? {
        var email: String = emailText.value.toString()
        if (email == "") {
            return "Please enter the email"
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return "Invalid email address"
        }
        return null
    }

    private fun validPassword(): String? {
        val passwordText = passwordText.value.toString()
        if (passwordText == "") {
            return "Please enter the password"
        }
        if (passwordText.length < 8) {
            return "Minimum 8 Character Password"
        }
        if (!passwordText.matches(".*[A-Z].*".toRegex())) {
            return "Must Contain 1 Upper-case Character"
        }
        if (!passwordText.matches(".*[a-z].*".toRegex())) {
            return "Must Contain 1 Lower-case Character"
        }
        if (!passwordText.matches(".*[@#\$%^&+=].*".toRegex())) {
            return "Must Contain 1 Special Character (@#\$%^&+=)"
        }
        return null
    }

}