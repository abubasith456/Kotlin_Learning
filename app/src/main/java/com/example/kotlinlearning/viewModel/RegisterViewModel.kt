package com.example.kotlinlearning.viewModel

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinlearning.R
import com.example.kotlinlearning.fragment.NewsFragment
import com.example.kotlinlearning.utils.Utils
import com.google.firebase.auth.FirebaseAuth

class RegisterViewModel : ViewModel() {

    var EmailRegister = MutableLiveData<String>()
    var PasswordRegister = MutableLiveData<String>()
    var NameRegister = MutableLiveData<String>()
    var EmailRegisterError = MutableLiveData<String?>()
    var PasswordRegisterError = MutableLiveData<String?>()
    var NameRegisterError = MutableLiveData<String?>()
    var EmailErrorVisible = MutableLiveData<Boolean>()
    var PasswordErrorVisible = MutableLiveData<Boolean>()
    var NameErrorVisible = MutableLiveData<Boolean>()
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    init {
        NameErrorVisible.value = false
        EmailErrorVisible.value = false;
        PasswordErrorVisible.value = false
    }

    @SuppressLint("StaticFieldLeak")
    private lateinit var activity: Activity

    fun getActivity(activity: Activity) {
        this.activity = activity
    }

    fun onRegisterClick() {
        try {
            val utils = Utils()
            if (utils.isNetworkConnectionAvailable(activity)) {
                if (validateRegister()) {
                    val email: String = EmailRegister.value.toString()
                    val password: String = PasswordRegister.value.toString()
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(activity) {
                            if (it.isSuccessful) {
                                Toast.makeText(
                                    activity,
                                    "Created successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                                val fragment = NewsFragment.newInstance();
                                val transaction =
                                    (activity as AppCompatActivity).supportFragmentManager.beginTransaction()
                                transaction.replace(R.id.frameLayoutContainer, fragment)
                                transaction.commit()
                            } else {
                                Toast.makeText(
                                    activity,
                                    "" + it.exception.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    EmailRegister.setValue("")
                    PasswordRegister.setValue("")
                    NameRegister.setValue("")
//                    ForgotPassword.setValue(null)
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

    fun validateRegister(): Boolean {
        EmailRegisterError.setValue(null)
        NameRegisterError.setValue(null)
        PasswordRegisterError.setValue(null)
        var valid = true
        try {
            if (NameRegister.value == null || NameRegister.value!!.isEmpty()) {
                NameErrorVisible.value = true
                NameRegisterError.setValue("Please enter the Username")
                valid = false
            } else {
                NameErrorVisible.value = false
                NameRegisterError.setValue("")
            }
            if (EmailRegister.value == null || EmailRegister.value!!.isEmpty()) {
                EmailErrorVisible.value = true
                EmailRegisterError.setValue("Please enter the email")
                valid = false
            } else {
                EmailErrorVisible.value = false
                EmailRegisterError.setValue("")
            }
            if (PasswordRegister.value == null || PasswordRegister.value!!.isEmpty()) {
                PasswordErrorVisible.value = true
                PasswordRegisterError.setValue("Please enter the password.")
                valid = false
            } else {
                PasswordErrorVisible.value = false
                PasswordRegisterError.setValue("")
            }
            if (!isEmailValid(EmailRegister.value)) {
                PasswordErrorVisible.value = true
                EmailRegisterError.setValue("Please enter a valid email address.")
                valid = false
            } else {
                PasswordErrorVisible.value = false
                EmailRegisterError.setValue("")
            }
        } catch (exception: java.lang.Exception) {
            Log.e("Error ==> ", "" + exception)
        }
        return valid
    }

    fun isEmailValid(value: String?): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(value).matches()
    }

}