package com.example.kotlinlearning.viewModel

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinlearning.R
import com.example.kotlinlearning.databinding.RegisterFragmentBinding
import com.example.kotlinlearning.fragment.NewsFragment
import com.example.kotlinlearning.utils.Utils
import com.google.firebase.auth.FirebaseAuth

class RegisterViewModel : ViewModel() {

    var emailRegister = MutableLiveData<String?>()
    var passwordRegister = MutableLiveData<String?>()
    var nameRegister = MutableLiveData<String?>()
    var emailRegisterError = ObservableField<String?>()
    var passwordRegisterError = ObservableField<String?>()
    var nameRegisterError = ObservableField<String?>()
    var processBarVisibility = ObservableField<Boolean>()
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    init {
        emailRegister.value = null
        passwordRegister.value = null
        nameRegister.value = null
        emailRegisterError.set(null)
        nameRegisterError.set(null)
        passwordRegisterError.set(null)
    }

    @SuppressLint("StaticFieldLeak")
    private lateinit var activity: Activity
    lateinit var binding: RegisterFragmentBinding

    fun getActivity(activity: Activity) {
        this.activity = activity
    }

    fun getBinding(binding: RegisterFragmentBinding) {
        this.binding = binding
    }

    fun onRegisterClick() {
        try {
            val utils = Utils()
            if (utils.isNetworkConnectionAvailable(activity)) {
                if (validateRegister()) {
                    processBarVisibility.set(true)
                    val email: String = emailRegister.value.toString()
                    val password: String = passwordRegister.value.toString()
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(activity) {
                            if (it.isSuccessful) {
                                processBarVisibility.set(false)
                                Toast.makeText(
                                    activity,
                                    "Created successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                                val fragment = NewsFragment.newInstance()
                                val transaction =
                                    (activity as AppCompatActivity).supportFragmentManager.beginTransaction()
                                transaction.replace(R.id.frameLayoutContainer, fragment)
                                transaction.commit()
                                clearEditTextField()
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
            } else {
                Toast.makeText(
                    activity,
                    "Please check the internet connection",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } catch (exception: Exception) {
            Log.e("Error register ==> ", "" + exception)
        }
    }

    fun clearEditTextField() {
        emailRegister.value = null
        passwordRegister.value = null
        nameRegister.value = null
        binding.editTextSignUpUserName.error = null
        binding.editTextSignUpEmail.error = null
        binding.editTextSignUpPassword.error = null
    }

    fun validateRegister(): Boolean {
        var valid = true
        try {
            if (nameRegister.value == null || nameRegister.value!!.isEmpty()) {
                nameRegisterError.set("Please enter the name.")
                valid = false
            }
            if (emailRegister.value == null || emailRegister.value!!.isEmpty()) {
                emailRegisterError.set("Please enter the email.")
                valid = false
            }
            if (passwordRegister.value == null || passwordRegister.value!!.isEmpty()) {
                passwordRegisterError.set("Please enter the password")
                valid = false
            }
            if (!isEmailValid(emailRegister.value)) {
                emailRegisterError.set("Please enter the valid email address")
                valid = false
            }
        } catch (exception: java.lang.Exception) {
            Log.e("Error ==> ", "" + exception)
        }
        return valid
    }

    fun isEmailValid(value: String?): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(value!!).matches()
    }

}