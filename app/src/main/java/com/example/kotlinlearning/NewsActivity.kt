package com.example.kotlinlearning

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinlearning.adapter.NewsAdapter
import com.example.kotlinlearning.model.Article
import com.example.kotlinlearning.model.NewsResponse
import com.example.kotlinlearning.rest_api.BCRequests
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.Exception
import kotlin.Throwable
import kotlin.toString

class NewsActivity : AppCompatActivity() {

    private val api = "a49844b91eb748bb9d3458aa0794db69"
    private val country = "in"
    private val category = "sports"
    lateinit var newsAdapter: NewsAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        showNews()
    }

    private fun showNews() {
        try {
            val call: Call<NewsResponse> =
                BCRequests().bCRestService.getNewsData(country, category, null, api)
            call.enqueue(object : Callback<NewsResponse> {
                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    if (response.isSuccessful()) {
                        Toast.makeText(this@NewsActivity, "Success", Toast.LENGTH_SHORT).show()
                        val responseBody = response.body()?.articles
                        val responseTotal = response.body()?.totalResults

                        newsAdapter = responseBody?.let { NewsAdapter(baseContext, it) }!!
                        newsAdapter.notifyDataSetChanged()
                        recyclerView.adapter = newsAdapter




                        for (myData in responseBody?.toList()!!) {
                            Log.e("Result==> ", myData.title)
                        }
                        Log.e("Result==> ", responseTotal.toString())
                    }
                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    Toast.makeText(this@NewsActivity, t.message, Toast.LENGTH_SHORT)
                        .show()
                    Log.e("Result==> ", t.message.toString())
                }
            })

        } catch (e: Exception) {
            Log.e("Api call error==> ", e.message.toString())
        }
    }
}