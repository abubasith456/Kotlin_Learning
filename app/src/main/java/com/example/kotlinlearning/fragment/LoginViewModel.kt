package com.example.kotlinlearning.fragment

import android.annotation.SuppressLint
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
import com.example.kotlinlearning.repository.FirebaseRepository
import com.example.kotlinlearning.utils.Utils

class LoginViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    var EmailLogin = MutableLiveData<String?>()
    var PasswordLogin = MutableLiveData<String?>()
    var EmailError = MutableLiveData<String?>()
    var PasswordError = MutableLiveData<String?>()
    var EmailRegister = MutableLiveData<String>()
    var PasswordRegister = MutableLiveData<String>()
    var NameRegister = MutableLiveData<String>()
    var EmailRegisterError = MutableLiveData<String>()
    var PasswordRegisterError = MutableLiveData<String>()
    var NameRegisterError = MutableLiveData<String>()
    var ForgotPassword = MutableLiveData<String>()
    var ForgotError = MutableLiveData<String>()

    @SuppressLint("StaticFieldLeak")
    private lateinit var activity: Activity
    lateinit var repository: FirebaseRepository

    fun getActivity(activity: Activity) {
        this.activity = activity
        this.repository= FirebaseRepository(activity)
    }

    fun onRegisterClick() {
        val registerFragmentFragment: Fragment = RegisterFragment()
        val transaction: FragmentTransaction =
            (activity as AppCompatActivity).supportFragmentManager.beginTransaction()
//            activity.getSupportFragmentManager().beginTransaction()
        transaction.replace(R.id.frameLayoutContainer, registerFragmentFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    //    public MutableLiveData<Boolean> onClickShow(View view) {
    //        EmailError.setValue(null);
    //        PasswordError.setValue(null);
    //        onClickResult.setValue(true);
    //        onClickRegister.setValue(true);
    //        return onClickResult;
    //    }
    //    public MutableLiveData<Boolean> onForgotPasswordClick(View view) {
    //        EmailError.setValue(null);
    //        PasswordError.setValue(null);
    //        onClickResult.setValue(true);
    //        onClickRegister.setValue(false);
    //        onClickForgotResult.setValue(true);
    //        return onClickResult;
    //    }
    fun onLoginClick() {
        try {
            val utils = Utils()
            if (utils.isNetworkConnectionAvailable(activity)) {
                if (validateLogin()) {
                    val email: String? =EmailLogin.value;
                    val password:String?=PasswordLogin.value
                    repository.login(email,password)
                    EmailLogin.value = null
                    PasswordLogin.value = null
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

    fun validateLogin(): Boolean {
        EmailError.setValue(null)
        PasswordError.setValue(null)
        var valid = true
        try {
            if (EmailLogin.value == null || EmailLogin.value!!.isEmpty()) {
                EmailError.setValue("Please enter email address.")
                valid = false
            }
            if (PasswordLogin.value == null || PasswordLogin.value!!.isEmpty()) {
                PasswordError.setValue("Please enter the password.")
                valid = false
            }
            if (!isEmailValid(EmailLogin.value)) {
                EmailError.setValue("Please enter a valid email address.")
                valid = false
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