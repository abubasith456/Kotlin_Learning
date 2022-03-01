package com.example.kotlinlearning.viewModel

import android.app.Application
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.kotlinlearning.SecondActivity
import com.example.kotlinlearning.databinding.ActivitySecondBinding
import com.google.firebase.auth.FirebaseAuth

class SecondActivityViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var dataBinding: ActivitySecondBinding
    var emailText = MutableLiveData<String>("")
    var passwordText = MutableLiveData<String>("")
    var userNameText = MutableLiveData<String>("")

    var emailTextHelper = MutableLiveData<String>("*Required")
    var passwordTextHelper = MutableLiveData<String>("*Required")
    var userNameTextHelper = MutableLiveData<String>("*Required")
    lateinit var secondActivity: Class<SecondActivity>


    var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun getViewModel(dataBinding: ActivitySecondBinding) {
        this.dataBinding = dataBinding
    }

    fun getActivity(secondActivity: Class<SecondActivity>) {
        this.secondActivity = secondActivity
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

    fun usernameOnFocused(value: Boolean) {
        if (!value) {
            userNameTextHelper.value = validUsername()
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

    private fun validUsername(): String? {
        val usernameText = userNameText.value.toString()
        if (usernameText == "") {
            return "Please enter the username"
        }
        return null
    }

    fun submitButtonClick() {

        if (emailValid() == null && validPassword() == null) {
            val email: String = emailText.value.toString()
            val password: String = passwordText.value.toString()
            try {
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(secondActivity.newInstance()) {

                        if (it.isSuccessful) {
                            resetField()
                            Toast.makeText(getApplication(), "Successfully logged", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(
                                getApplication(),
                                "" + it.exception.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            } catch (e: Exception) {
                Log.e("Firebase error", e.message.toString())
            }
        }
    }

    private fun resetField() {
        emailTextHelper.value = "*Required"
        passwordTextHelper.value = "*Required"
        userNameTextHelper.value = "*Required"

        emailText.value = ""
        passwordText.value = ""
        userNameText.value = ""
    }


//    private fun checkMail(): Boolean {
//        var checked = true
//        var email: String? = emailText.value.toString()
//
//        if (email.equals("")) {
//            emailTextHelper.value = "Please enter the email"
//            checked = false
//        }
//        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            emailTextHelper.value = "Please enter the valid email address"
//            checked = false
//        }
//        return checked
//    }
//
//    private fun checkPassword(): Boolean {
//        var checked = true
//        var password: String = passwordText.value.toString()
//
//        if (password.length < 8) {
//            passwordTextHelper.value = "Minimum 8 Character Password"
//        }
//        if (!password.matches(".*[A-Z].*".toRegex())) {
//            passwordTextHelper.value = "Must Contain 1 Upper-case Character"
//        }
//        if (!password.matches(".*[a-z].*".toRegex())) {
//            passwordTextHelper.value = "Must Contain 1 Lower-case Character"
//        }
//        if (!password.matches(".*[@#\$%^&+=].*".toRegex())) {
//            passwordTextHelper.value = "Must Contain 1 Special Character (@#\$%^&+=)"
//        } else {
//            passwordTextHelper.value = "Nothing"
//        }
//        return checked
//    }
//
//    private fun email(): MutableLiveData<String> {
//        return emailText
//    }
//
//    private fun validEmail(): String? {
//        var email = dataBinding.emailEditText.text.toString()
//        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            return "Invalid email address"
//        }
//        return null
//    }


}