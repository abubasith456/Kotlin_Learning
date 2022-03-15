package com.example.kotlinlearning.viewModel

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinlearning.R
import com.example.kotlinlearning.adapter.NewsAdapter
import com.example.kotlinlearning.databinding.NewsFragmentBinding
import com.example.kotlinlearning.fragment.NewsCategoryFragment
import com.example.kotlinlearning.fragment.NewsFragment
import com.example.kotlinlearning.model.Article
import com.example.kotlinlearning.model.Category
import com.example.kotlinlearning.model.NewsResponse
import com.example.kotlinlearning.rest_api.BCRequests
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel : ViewModel() {

    lateinit var binding: NewsFragmentBinding

    private val api = "a49844b91eb748bb9d3458aa0794db69"
    private val country = "in"
    private val category = "general"
    lateinit var newsAdapter: NewsAdapter
    private val newsHeadlines: MutableLiveData<List<Article>> =
        MutableLiveData<List<Article>>()

    private val newsCategoryHeadlines: MutableLiveData<List<Article>> =
        MutableLiveData<List<Article>>()


    lateinit var activity: Activity
    lateinit var newsFragment: Class<NewsFragment>

    fun getActivity(activity: Activity) {
        this.activity = activity
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
                        Toast.makeText(
                            activity,
                            "" + selectedCategory,
                            Toast.LENGTH_SHORT
                        ).show()
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

//        try {
//            val call: Call<NewsResponse> =
//                BCRequests().bCRestService.getNewsData(country, selectedCategory, null, api)
//            call.enqueue(object : Callback<NewsResponse> {
//                override fun onResponse(
//                    call: Call<NewsResponse>,
//                    response: Response<NewsResponse>
//                ) {
//                    if (response.isSuccessful()) {
//                        Toast.makeText(
//                            activity,
//                            "Success",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                        val responseBody = response.body()?.articles
////                        newsAdapter = responseBody?.let { NewsAdapter(activity, it) }!!
////                        newsAdapter.notifyDataSetChanged()
////                        binding.recyclerView.adapter = newsAdapter
//                        newsCategoryHeadlines.value = responseBody!!
//                    }
//                }
//
//                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
//                    Toast.makeText(
//                        activity,
//                        t.message,
//                        Toast.LENGTH_SHORT
//                    )
//                        .show()
//                    Log.e("Result==> ", t.message.toString())
//                }
//            })
//
//        } catch (e: Exception) {
//            Log.e("Api call error==> ", e.message.toString())
//        }

    }

    fun onSettingClick() {
        val category = Category()
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Settings").setItems(
            category.selectSettings,
            DialogInterface.OnClickListener { dialogInterface, which ->
                val selectedCategory: String = category.selectSettings.get(which)
                Toast.makeText(
                    activity,
                    "" + selectedCategory,
                    Toast.LENGTH_SHORT
                ).show()
            }).show()
    }

    fun getCategoryHeadlines(): LiveData<List<Article>> {
        return newsCategoryHeadlines
    }

    fun getNewsHeadlines(): LiveData<List<Article>> {
        try {
            val call: Call<NewsResponse> =
                BCRequests().bCRestService.getNewsData(country, category, null, api)
            call.enqueue(object : Callback<NewsResponse> {
                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    if (response.isSuccessful()) {
                        Toast.makeText(
                            activity,
                            "Success",
                            Toast.LENGTH_SHORT
                        ).show()
                        val responseBody = response.body()?.articles
                        val responseTotal = response.body()?.totalResults

//                        newsAdapter = responseBody?.let { NewsAdapter(activity, it) }!!
//                        newsAdapter.notifyDataSetChanged()
//                        binding.recyclerView.adapter = newsAdapter
                        newsHeadlines.value = responseBody!!
                    }
                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
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


}