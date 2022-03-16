package com.example.kotlinlearning.viewModel

import android.app.Activity
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinlearning.R
import com.example.kotlinlearning.databinding.LoginFragmentBinding
import com.example.kotlinlearning.fragment.ForgotPasswordFragment
import com.example.kotlinlearning.fragment.LoginFragment
import com.example.kotlinlearning.fragment.NewsFragment
import com.example.kotlinlearning.fragment.RegisterFragment
import com.example.kotlinlearning.repository.FirebaseRepository
import com.example.kotlinlearning.utils.Utils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    var EmailLogin: MutableLiveData<String?> = MutableLiveData()
    var PasswordLogin: MutableLiveData<String?> = MutableLiveData()
    var EmailError: MutableLiveData<String> = MutableLiveData()
    var EmailErrorVisible: MutableLiveData<Boolean> = MutableLiveData()
    var PasswordError: MutableLiveData<String> = MutableLiveData()
    var PasswordErrorVisible: MutableLiveData<Boolean> = MutableLiveData()
    var EmailRegister = MutableLiveData<String>()
    var PasswordRegister = MutableLiveData<String>()
    var NameRegister = MutableLiveData<String>()
    var EmailRegisterError = MutableLiveData<String>()
    var PasswordRegisterError = MutableLiveData<String>()
    var NameRegisterError = MutableLiveData<String>()
    var ForgotPassword = MutableLiveData<String>()
    var ForgotError = MutableLiveData<String>()
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    lateinit var loginLiveData: MutableLiveData<FirebaseUser>

    init {

        EmailLogin.value = null
        PasswordLogin.value = null
        EmailError.value = ""
        EmailErrorVisible.value = false
        PasswordError.value = ""
        PasswordErrorVisible.value = false
    }

    private lateinit var activity: Activity
    private lateinit var fragmentBinding: LoginFragmentBinding
    lateinit var repository: FirebaseRepository

    fun getActivity(activity: Activity) {
        this.activity = activity
        this.repository = FirebaseRepository(activity)
    }

    fun getBinding(fragmentBinding: LoginFragmentBinding) {
        this.fragmentBinding = fragmentBinding
    }

    fun onRegisterClick() {
        val fragment: Fragment = RegisterFragment.newInstance()
        val transaction: FragmentTransaction =
            (activity as AppCompatActivity).supportFragmentManager.beginTransaction()
//            activity.getSupportFragmentManager().beginTransaction()
        transaction.replace(R.id.frameLayoutContainer, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
        resetTextfields()
    }

    fun onLoginClick() {
        try {
            val utils = Utils()
            if (utils.isNetworkConnectionAvailable(activity)) {
                if (validateLogin()) {
                    val email: String? = EmailLogin.value;
                    val password: String? = PasswordLogin.value
                    try {
                        if (email != null && password != null) {
                            firebaseAuth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener(activity) {
                                    if (it.isSuccessful) {
                                        val fragment = NewsFragment.newInstance();
                                        val transaction =
                                            (activity as AppCompatActivity).supportFragmentManager.beginTransaction()
                                        transaction.replace(R.id.frameLayoutContainer, fragment)
                                        transaction.commit()
                                        resetTextfields()
                                    } else {
                                        Toast.makeText(
                                            activity,
                                            it.exception.toString(),
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                        }
                    } catch (exception: Exception) {
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

    private fun resetTextfields() {
        EmailLogin.value = ""
        PasswordLogin.value = ""
        fragmentBinding.editTextEmailInput.error = null
        fragmentBinding.editTextPasswordInput.error = null
    }

    fun validateLogin(): Boolean {
//        EmailError.setValue(null)
//        PasswordError.setValue(null)
        var valid = true
        try {
            if (EmailLogin.value == null || EmailLogin.value!!.isEmpty()) {
//                EmailErrorVisible.value = true
//                fragmentBinding.textViewErrorEmail.text = "Please enter email address."
                fragmentBinding.editTextEmailInput.error = "Please enter email address."
                valid = false
            }
            if (PasswordLogin.value == null || PasswordLogin.value!!.isEmpty()) {
                fragmentBinding.editTextPasswordInput.setError("Please enter password")
//                PasswordErrorVisible.value = true
//                fragmentBinding.textViewErrorPassword.text = "Please enter the password"
//                PasswordError.value = "Please enter the password."
                valid = false
            }
            if (!isEmailValid(EmailLogin.value)) {
//                EmailErrorVisible.value = true
//                fragmentBinding.textViewErrorEmail.text = "Please enter the valid email"
                fragmentBinding.editTextEmailInput.error = "Please enter the valid email address"
                valid = false
            }
        } catch (exception: java.lang.Exception) {
            Log.e("Error ==> ", "" + exception)
        }
        return valid
    }

    fun onForgotClick() {
        try {
            val fragment: Fragment = ForgotPasswordFragment.newInstance()
            val transaction: FragmentTransaction =
                (activity as AppCompatActivity).supportFragmentManager.beginTransaction()
//            activity.getSupportFragmentManager().beginTransaction()
            transaction.replace(R.id.frameLayoutContainer, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        } catch (exception: java.lang.Exception) {
            Log.e("Error ==> ", "" + exception)
        }
    }

    fun isEmailValid(value: String?): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(value).matches()
    }
}