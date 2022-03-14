package com.example.kotlinlearning.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.kotlinlearning.databinding.NewsDetailsFragmentBinding
import com.example.kotlinlearning.model.detailsModel.DetailsModel
import com.example.kotlinlearning.viewModel.NewsDetailsViewModel

class NewsDetailsFragment(
    val title: String,
    val description: String,
    val content: String,
    val urlToImage: String,
    val url: String,
    val author: String,
    val publishedAt: String
) : Fragment() {

    private lateinit var viewModel: NewsDetailsViewModel
    private lateinit var binding: NewsDetailsFragmentBinding
    lateinit var detailsModel: DetailsModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = NewsDetailsFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NewsDetailsViewModel::class.java)
        DetailsModel(title, description, author, publishedAt, urlToImage, url, content)
        Toast.makeText(activity, title, Toast.LENGTH_SHORT).show()
    }

}