package com.example.kotlinlearning.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinlearning.adapter.NewsAdapter
import com.example.kotlinlearning.databinding.NewsFragmentBinding
import com.example.kotlinlearning.model.Article
import com.example.kotlinlearning.viewModel.NewsViewModel

class NewsFragment : Fragment() {

    companion object {
        fun newInstance() = NewsFragment()
    }

    private lateinit var viewModel: NewsViewModel
    private lateinit var binding: NewsFragmentBinding
    lateinit var newsAdapter: NewsAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
    var newsHeadLinesList = mutableListOf<Article>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = NewsFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        activity?.let { viewModel.getActivity(it) }
        binding.newsViewModel = viewModel
        binding.recyclerView.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = linearLayoutManager
        newsHeadLinesList.clear()
        viewModel.getNewsHeadlines().observe(requireActivity()) {
//            newsHeadLinesList.clear()
            Log.e("Articles==> ", it.get(0).title)
//            val size = newsHeadLinesList.size
//
//            for (i in 0..size) {
//                Log.e("For==>", i.toString())
//                newsHeadLinesList.add(it[i])
//            }
//            for (i in 0..size - 1) {
//                Log.e("For==>",i.toString())
//                newsHeadLinesList.add(it[i])
//            }
            newsAdapter = NewsAdapter(requireActivity(), it)
            Log.e("Articles size==> ", it.size.toString())
            newsAdapter.notifyDataSetChanged()
            binding.recyclerView.adapter = newsAdapter

        }

        viewModel.getCategoryHeadlines().observe(requireActivity()) {
            newsHeadLinesList.clear()
            val size = newsHeadLinesList.size
            for (i in 0..size) {
                Log.e("For==>", i.toString())
                newsHeadLinesList.add(it[i])
            }
            newsAdapter = NewsAdapter(requireActivity(), newsHeadLinesList)
            newsAdapter.notifyDataSetChanged()
            binding.recyclerView.adapter = newsAdapter
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

}