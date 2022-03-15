package com.example.kotlinlearning.viewModel

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinlearning.model.Article
import com.example.kotlinlearning.model.NewsResponse
import com.example.kotlinlearning.rest_api.BCRequests
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsCategoryViewModel : ViewModel() {

    private val api = "a49844b91eb748bb9d3458aa0794db69"
    private val country = "in"
    lateinit var category: String
    private val newsCategoryHeadlines: MutableLiveData<List<Article>> =
        MutableLiveData<List<Article>>()

    lateinit var activity: Activity
    fun getActivity(activity: Activity) {
        this.activity = activity
    }

    fun getCategory(category: String) {
        this.category = category
    }

    fun getCategoryHeadlines(): LiveData<List<Article>> {

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
//                        newsAdapter = responseBody?.let { NewsAdapter(activity, it) }!!
//                        newsAdapter.notifyDataSetChanged()
//                        binding.recyclerView.adapter = newsAdapter
                        newsCategoryHeadlines.value = responseBody!!
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
        return newsCategoryHeadlines
    }

}