package com.example.kotlinlearning.viewModel

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.res.Resources
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinlearning.R
import com.example.kotlinlearning.adapter.NewsAdapter
import com.example.kotlinlearning.databinding.NewsFragmentBinding
import com.example.kotlinlearning.fragment.LoginFragment
import com.example.kotlinlearning.fragment.NewsCategoryFragment
import com.example.kotlinlearning.fragment.OfflineNewsFragment
import com.example.kotlinlearning.model.Article
import com.example.kotlinlearning.model.Category
import com.example.kotlinlearning.model.NewsResponse
import com.example.kotlinlearning.rest_api.BCRequests
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel : ViewModel() {

    lateinit var binding: NewsFragmentBinding
    private val country = "in"
    private val category = "general"
    val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val newsHeadlines: MutableLiveData<List<Article>> =
        MutableLiveData<List<Article>>()
    var processBarVisibility: ObservableField<Boolean> = ObservableField()

    init {
        processBarVisibility.set(true)
    }

    lateinit var activity: Activity

    fun getActivity(activity: Activity) {
        this.activity = activity
    }

    fun getNewsHeadlines(): LiveData<List<Article>> {
        try {
            val res: Resources = activity.getResources()
            val call: Call<NewsResponse> =
                BCRequests().bCRestService.getNewsData(
                    country,
                    category,
                    null,
                    res.getString(R.string.api)
                )
            call.enqueue(object : Callback<NewsResponse> {
                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    if (response.isSuccessful()) {
                        processBarVisibility.set(false)
                        Toast.makeText(
                            activity,
                            "Success",
                            Toast.LENGTH_SHORT
                        ).show()
                        val responseBody = response.body()?.articles
                        newsHeadlines.value = responseBody!!
                    }
                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    processBarVisibility.set(false)
                    Toast.makeText(
                        activity,
                        t.message,
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    Log.e("Result==> ", t.message.toString())
                }
            })

        } catch (e: Exception) {
            Log.e("Api call error==> ", e.message.toString())
        }
        return newsHeadlines
    }

    fun onCategoryClick() {
        try {
            val category = Category()
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("Select Category")
                .setItems(
                    category.selectCategory,
                    DialogInterface.OnClickListener { dialog, which ->
                        val selectedCategory: String = category.selectCategory.get(which)
                        loadFilterItems(selectedCategory)
                    }).show()

        } catch (e: Exception) {
            Log.e("Api call error==> ", e.message.toString())
        }
    }

    private fun loadFilterItems(selectedCategory: String) {
        val fragment = NewsCategoryFragment(selectedCategory)
        val transaction = (activity as AppCompatActivity).supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayoutContainer, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun onSettingClick() {
        val category = Category()
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Settings").setItems(
            category.selectSettings,
            DialogInterface.OnClickListener { dialogInterface, which ->
                val selectedCategory: String = category.selectSettings.get(which)
                moveToselectedPage(selectedCategory);
            }).show()
    }

    private fun moveToselectedPage(selectedCategory: String) {
        try {
            if (selectedCategory.equals("Offline news")) {
                val fragment = OfflineNewsFragment()
                val transaction =
                    (activity as AppCompatActivity).supportFragmentManager.beginTransaction()
                transaction.replace(R.id.frameLayoutContainer, fragment)
                transaction.addToBackStack(null)
                transaction.commit()
            } else if (selectedCategory.equals("Logout")) {
                firebaseAuth.signOut()
                val fragment = LoginFragment()
                val transaction =
                    (activity as AppCompatActivity).supportFragmentManager.beginTransaction()
                transaction.replace(R.id.frameLayoutContainer, fragment)
                transaction.commit()
            }
        } catch (e: Exception) {
            Log.e("Api call error==> ", e.message.toString())
        }
    }

}