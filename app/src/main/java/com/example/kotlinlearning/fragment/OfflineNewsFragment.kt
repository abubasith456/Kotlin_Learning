package com.example.kotlinlearning.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinlearning.R
import com.example.kotlinlearning.adapter.OfflineNewsAdapter
import com.example.kotlinlearning.databinding.OfflineNewsFragmentBinding
import com.example.kotlinlearning.db.NewsEntity
import com.example.kotlinlearning.viewModel.OfflineNewsViewModel

class OfflineNewsFragment : Fragment() {

    companion object {
        fun newInstance() = OfflineNewsFragment()
    }

    private lateinit var viewModel: OfflineNewsViewModel
    private lateinit var binding: OfflineNewsFragmentBinding
    private lateinit var newsAdapter: OfflineNewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = OfflineNewsFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(OfflineNewsViewModel::class.java)
        binding.offlineNewsViewModel = viewModel
        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewOfflineNews.layoutManager = linearLayoutManager
        binding.recyclerViewOfflineNews.setHasFixedSize(true)

        viewModel.getOfflineNewsHeadlines().observe(requireActivity()) {
            try {
                Log.e("Offline db count==> ", it?.size.toString())
                newsAdapter = OfflineNewsAdapter(requireContext(), it!! as MutableList<NewsEntity>)
                binding.recyclerViewOfflineNews.adapter = newsAdapter
                newsAdapter.notifyDataSetChanged()
            } catch (exception: Exception) {
                Log.e("Error offline db==> ", exception.message.toString())
            }
        }
    }

}