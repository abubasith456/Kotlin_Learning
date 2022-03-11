package com.example.kotlinlearning.fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    var EmailLogin = MutableLiveData<String>()
    var PasswordLogin = MutableLiveData<String>()
    var EmailError = MutableLiveData<String>()
    var PasswordError = MutableLiveData<String>()
    var EmailRegister = MutableLiveData<String>()
    var PasswordRegister = MutableLiveData<String>()
    var NameRegister = MutableLiveData<String>()
    var EmailRegisterError = MutableLiveData<String>()
    var PasswordRegisterError = MutableLiveData<String>()
    var NameRegisterError = MutableLiveData<String>()
    var ForgotPassword = MutableLiveData<String>()
    var ForgotError = MutableLiveData<String>()

    fun onRegisterClick(){



    }
}