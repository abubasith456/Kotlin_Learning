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
    var EmailLogin = MutableLiveData<String?>()
    var PasswordLogin = MutableLiveData<String?>()
    var EmailError = MutableLiveData<String?>()
    var EmailErrorVisible = MutableLiveData<Boolean>()
    var PasswordError = MutableLiveData<String?>()
    var PasswordErrorVisible = MutableLiveData<Boolean>()
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
//    val errorMessage = MutableLiveData<Int>()

    //    interface LoginViewModel {
//        val errorMessage: LiveData<Int>
//    }
    init {
        EmailLogin.value = ""
        PasswordLogin.value = ""
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
        EmailErrorVisible.value = false
        PasswordErrorVisible.value = false
    }

    fun validateLogin(): Boolean {
//        EmailError.setValue(null)
//        PasswordError.setValue(null)
        var valid = true
        try {
            if (EmailLogin.value == null || EmailLogin.value!!.isEmpty()) {
                Toast.makeText(activity, "Please enter email address.", Toast.LENGTH_SHORT).show()
                EmailErrorVisible.postValue(true)
                EmailError.value = "Please enter email address."
                valid = false
            } else {
                EmailErrorVisible.value = false
                EmailError.value = ""
            }
            if (PasswordLogin.value == null || PasswordLogin.value!!.isEmpty()) {
                Toast.makeText(activity, "Please enter the password.", Toast.LENGTH_SHORT).show()
                PasswordErrorVisible.value = true
                PasswordError.value = "Please enter the password."
                valid = false
            } else {
                PasswordErrorVisible.value = false
                PasswordError.value = ""
            }
            if (!isEmailValid(EmailLogin.value)) {
                Toast.makeText(activity, "Please enter a valid email address.", Toast.LENGTH_SHORT)
                    .show()
                EmailErrorVisible.value = true
                EmailError.value = "Please enter a valid email address."
                valid = false
            } else {
                EmailErrorVisible.value = false
                EmailError.value = ""
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