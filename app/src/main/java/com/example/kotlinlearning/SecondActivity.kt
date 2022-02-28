package com.example.kotlinlearning

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import androidx.lifecycle.ViewModelProviders
import com.example.kotlinlearning.databinding.ActivitySecondBinding
import com.example.kotlinlearning.viewModel.SecondActivityViewModel

lateinit var dataBinding: ActivitySecondBinding
lateinit var viewModel: SecondActivityViewModel

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(dataBinding.root)
        viewModel = ViewModelProviders.of(this).get(SecondActivityViewModel::class.java)
        dataBinding.apply {
            this.viewModelSecondActivity = viewModel
            this.setLifecycleOwner(this@SecondActivity)
        }
        viewModel.getViewModel(dataBinding)

        emailFocusListener()
        passwordFocusListener()
        usernameFocusListener()

//        viewModel.emailText.observe(this, Observer {
//            if (it.isEmpty()){
//                dataBinding.emailTextLayout.helperText="Required"
//            }
//           if(!Patterns.EMAIL_ADDRESS.matcher(it).matches()){
//               dataBinding.emailTextLayout.helperText="Invalid email address"
//           }else{
//               dataBinding.emailTextLayout.helperText=null
//           }
//        })

    }
    //
//    private fun emailValidate() {
//        viewModel.emailText.observe(this, Observer {
//
//            dataBinding.emailTextLayout.helperText = emailValid()
//
//        })
//    }


    private fun emailFocusListener() {
        dataBinding.emailEditText.setOnFocusChangeListener { view, focused ->
//            if (!value) {
//                dataBinding.emailTextLayout.helperText = emailValid()
//            }
            viewModel.emailOnFocused(focused)
        }

    }

    private fun passwordFocusListener() {
        dataBinding.passwordEditText.setOnFocusChangeListener { _, focused ->
//            if (!focused) {
//                dataBinding.passwordTextLayout.helperText = validPassword()
//            }
            viewModel.passwordOnFocused(focused)
        }
    }

    private fun usernameFocusListener(){
        dataBinding.editTextUsername.setOnFocusChangeListener { _, focused ->
            viewModel.usernameOnFocused(focused)
        }
    }

//    private fun emailValid(): String? {
//        var email = dataBinding.emailEditText.text.toString()
//        if (email.isEmpty()) {
//            return "Please enter the value"
//        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            return "Invalid email address"
//        }
//        return null
//    }
//
//    private fun validPassword(): String? {
//        val passwordText = dataBinding.passwordEditText.text.toString()
//        if (passwordText.length < 8) {
//            return "Minimum 8 Character Password"
//        }
//        if (!passwordText.matches(".*[A-Z].*".toRegex())) {
//            return "Must Contain 1 Upper-case Character"
//        }
//        if (!passwordText.matches(".*[a-z].*".toRegex())) {
//            return "Must Contain 1 Lower-case Character"
//        }
//        if (!passwordText.matches(".*[@#\$%^&+=].*".toRegex())) {
//            return "Must Contain 1 Special Character (@#\$%^&+=)"
//        }
//        return null
//    }
}