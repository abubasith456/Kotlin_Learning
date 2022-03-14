package com.example.kotlinlearning.repository

import android.app.Activity
import android.util.Log
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
        Log.e("Get activity", activity.toString())
    }

    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    lateinit var loginLiveData: MutableLiveData<FirebaseUser>


    fun getLoggedUser(): MutableLiveData<FirebaseUser> {
        return loginLiveData
    }

    fun login(email: String?, password: String?) {
        if (password != null && email != null) {
            try {
                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(activity) {
                        if (it.isSuccessful) {
                            val fragment = NewsFragment.newInstance();
                            val transaction =
                                (activity as AppCompatActivity).supportFragmentManager.beginTransaction()
                            transaction.replace(R.id.frameLayoutContainer, fragment)
                            transaction.commit()
                        } else {
                            Log.e("Firebase error==> ", it.exception.toString())
                        }
                    }

            } catch (exception: Exception) {
                Log.e("Error==>", exception.message.toString())
            }

//                .let {
//                    if (it) {
//
//                    } else {
//                        Log.e("Firebase error=> ", it.toString())
//                    }
//                }
        }
    }
}