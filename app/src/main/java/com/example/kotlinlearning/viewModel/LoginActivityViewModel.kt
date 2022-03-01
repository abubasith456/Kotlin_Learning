package com.example.kotlinlearning.viewModel

import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.kotlinlearning.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import android.app.Application
import androidx.lifecycle.AndroidViewModel

class LoginActivityViewModel(application: Application) : AndroidViewModel(application) {

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

    fun loginButtonPressed() {
        if (emailValid() == null && validPassword() == null) {
            val email: String = emailText.value.toString()
            val password: String = passwordText.value.toString()
            try {
                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(loginActivity.newInstance()) {
                        if (it.isSuccessful) {
                            Toast.makeText(
                                getApplication(),
                                "Login successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                            resetField()
                        } else {
                            Toast.makeText(
                                getApplication(),
                                it.exception.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

            } catch (e: Exception) {
                Log.e("Firebase error==> ", e.message.toString())
            }
        }else{
            emailTextHelper.value = emailValid()
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

    private fun resetField() {
        emailTextHelper.value = "*Required"
        passwordTextHelper.value = "*Required"

        emailText.value = ""
        passwordText.value = ""
    }

}