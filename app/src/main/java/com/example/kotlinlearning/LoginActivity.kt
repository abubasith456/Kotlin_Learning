package com.example.kotlinlearning

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.example.kotlinlearning.databinding.ActivityLoginBinding
import com.example.kotlinlearning.viewModel.LoginActivityViewModel


class LoginActivity : AppCompatActivity() {

    lateinit var loginActivityViewModel: LoginActivityViewModel
    lateinit var activityLoginBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginActivityViewModel = ViewModelProviders.of(this).get(LoginActivityViewModel::class.java)
        activityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(activityLoginBinding.root)
        activityLoginBinding.apply {
            this.loginViewModel = loginActivityViewModel
            this.lifecycleOwner = this@LoginActivity
        }

        loginActivityViewModel.getActivity(LoginActivity::class.java)

        emailFocusListener()
        passwordFocusListener()
    }

    private fun passwordFocusListener() {
        activityLoginBinding.emailEditText.setOnFocusChangeListener { view, focused ->
            loginActivityViewModel.emailOnFocused(focused)
        }
    }

    private fun emailFocusListener() {
        activityLoginBinding.passwordEditText.setOnFocusChangeListener { view, focused ->
            loginActivityViewModel.passwordOnFocused(focused)
        }
    }
}