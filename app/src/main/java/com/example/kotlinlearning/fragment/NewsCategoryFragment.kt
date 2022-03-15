package com.example.kotlinlearning.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinlearning.R
import com.example.kotlinlearning.adapter.NewsAdapter
import com.example.kotlinlearning.databinding.NewsCategoryFragmentBinding
import com.example.kotlinlearning.viewModel.NewsCategoryViewModel

class NewsCategoryFragment(category: String) : Fragment() {

    //    companion object {
//        fun newInstance() = NewsCategoryFragment()
//    }
    private lateinit var binding: NewsCategoryFragmentBinding
    private lateinit var viewModel: NewsCategoryViewModel
    private var category: String = category
    lateinit var newsAdapter: NewsAdapter
    lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = NewsCategoryFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NewsCategoryViewModel::class.java)
        binding.newsCategoryViewModel = viewModel
        linearLayoutManager= LinearLayoutManager(requireContext())
        binding.recyclerViewNewsCategory.setHasFixedSize(true)
        binding.recyclerViewNewsCategory.layoutManager=linearLayoutManager
        activity?.let { viewModel.getActivity(it) }
        Log.e("category==> ", category)
        viewModel.getCategory(category)

        viewModel.getCategoryHeadlines().observe(requireActivity()){
            Log.e("Articles category==> ", it.get(0).title)
            newsAdapter = NewsAdapter(requireActivity(), it)
            Log.e("Articles size==> ", it.size.toString())
            newsAdapter.notifyDataSetChanged()
            binding.recyclerViewNewsCategory.adapter = newsAdapter

        }
    }

}