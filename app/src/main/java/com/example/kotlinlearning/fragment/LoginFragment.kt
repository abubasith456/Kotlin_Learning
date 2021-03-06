package com.example.kotlinlearning.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlinlearning.databinding.LoginFragmentBinding
import com.example.kotlinlearning.viewModel.LoginViewModel

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel
    lateinit var fragmentBinding: LoginFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentBinding = LoginFragmentBinding.inflate(inflater, container, false);
        return fragmentBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        viewModel.apply {
            activity?.let {
                this.getActivity(it)
                this.getBinding(fragmentBinding)
            }
        }
        fragmentBinding.apply {
            this.loginViewModel = viewModel
        }
    }

}