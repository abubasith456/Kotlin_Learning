package com.example.kotlinlearning.repository

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.example.kotlinlearning.R
import com.example.kotlinlearning.fragment.NewsFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class FirebaseRepository(activity: Activity) {
    val activity: Activity

    init {
        this.activity = activity
    }

    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    lateinit var loginLiveData: MutableLiveData<FirebaseUser>


    fun getLoggedUser(): MutableLiveData<FirebaseUser> {
        return loginLiveData
    }

    fun login(email: String?, password: String?) {
        if (password != null && email != null) {
            firebaseAuth.signInWithEmailAndPassword(email, password).isComplete.let {
                if (it) {
                    val fragment = NewsFragment.newInstance();
                    val transaction =
                        (activity as AppCompatActivity).supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.frameLayoutContainer, fragment)
                    transaction.commit()
                }
            }
        }
    }
}