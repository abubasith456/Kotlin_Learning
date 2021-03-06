package com.example.kotlinlearning.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlinlearning.databinding.ForgotPasswordFragmentBinding
import com.example.kotlinlearning.viewModel.ForgotPasswordViewModel

class ForgotPasswordFragment(var email:String?) : Fragment() {

    private lateinit var viewModel: ForgotPasswordViewModel
    private lateinit var binding: ForgotPasswordFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ForgotPasswordFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ForgotPasswordViewModel::class.java)
        binding.forgotViewModel = viewModel
        viewModel.getBinding(binding,email)
        // TODO: Use the ViewModel
    }

}