package com.example.kotlinlearning.viewModel

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ObservableField
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinlearning.R
import com.example.kotlinlearning.databinding.LoginFragmentBinding
import com.example.kotlinlearning.fragment.ForgotPasswordFragment
import com.example.kotlinlearning.fragment.NewsFragment
import com.example.kotlinlearning.fragment.RegisterFragment
import com.example.kotlinlearning.utils.Utils
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    var emailLogin = MutableLiveData<String?>()
    var passwordLogin = MutableLiveData<String?>()
    var emailError = ObservableField<String>()
    var passwordError = ObservableField<String>()
    private val utils = Utils()
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    var processBarVisibility = ObservableField<Boolean>()

    init {
        emailLogin.value = null
        passwordLogin.value = null
        emailError.set(null)
        passwordError.set(null)
    }

    @SuppressLint("StaticFieldLeak")
    private lateinit var activity: Activity
    private lateinit var fragmentBinding: LoginFragmentBinding

    fun getActivity(activity: Activity) {
        this.activity = activity
    }

    fun getBinding(fragmentBinding: LoginFragmentBinding) {
        this.fragmentBinding = fragmentBinding
    }

    fun onForgotClick() {
        try {
            val fragment = ForgotPasswordFragment(emailLogin.value.toString())
            val transaction: FragmentTransaction =
                (activity as AppCompatActivity).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frameLayoutContainer, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
            emailError.set(null)
            passwordError.set(null)
//            resetText()
        } catch (exception: java.lang.Exception) {
            Log.e("Error ==> ", "" + exception)
        }
    }

    fun onRegisterClick() {
        val fragment: Fragment = RegisterFragment.newInstance()
        val transaction: FragmentTransaction =
            (activity as AppCompatActivity).supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayoutContainer, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
        resetText()
    }

    fun onLoginClick() {
        try {
            if (utils.isNetworkConnectionAvailable(activity)) {
                if (validateLogin()) {
                    processBarVisibility.set(true)
                    val email: String? = emailLogin.value
                    val password: String? = passwordLogin.value
                    try {
                        if (email != null && password != null) {
                            firebaseAuth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener(activity) {
                                    if (it.isSuccessful) {
                                        processBarVisibility.set(false)
                                        val fragment = NewsFragment.newInstance()
                                        val transaction =
                                            (activity as AppCompatActivity).supportFragmentManager.beginTransaction()
                                        transaction.replace(R.id.frameLayoutContainer, fragment)
                                        transaction.commit()
                                        resetText()
                                    } else {
                                        processBarVisibility.set(false)
                                        Toast.makeText(
                                            activity,
                                            "" + it.exception?.message.toString(),
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                        }
                    } catch (exception: Exception) {
                        processBarVisibility.set(false)
                        Log.e("Error==>", exception.message.toString())
                    }
                }
            } else {
                Toast.makeText(
                    activity,
                    "Please check the internet connection",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } catch (exception: Exception) {
            Log.e("Error login==> ", "" + exception)
        }
    }

    private fun resetText() {
        emailLogin.value = null
        passwordLogin.value = null
        emailError.set(null)
        passwordError.set(null)
    }

    private fun validateLogin(): Boolean {
        var valid = true
        try {
            if (emailLogin.value == null || emailLogin.value!!.isEmpty()) {
                emailError.set("Please enter email address.")
                valid = false
            }
            if (passwordLogin.value == null || passwordLogin.value!!.isEmpty()) {
                passwordError.set("Please enter the password.")
                valid = false
            }
            if (!isEmailValid(emailLogin.value)) {
                emailError.set("Please enter the valid email address")
                valid = false
            }
        } catch (exception: java.lang.Exception) {
            Log.e("Error ==> ", "" + exception)
        }
        return valid
    }

    private fun isEmailValid(value: String?): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(value!!).matches()
    }
}