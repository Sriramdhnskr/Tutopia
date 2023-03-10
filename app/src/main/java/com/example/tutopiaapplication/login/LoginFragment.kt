package com.example.tutopiaapplication.login

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.tutopiaapplication.R
import com.example.tutopiaapplication.databinding.FragmentLoginBinding
import com.example.tutopiaapplication.utils.Constants
import com.example.tutopiaapplication.utils.Listener


class LoginFragment : Fragment(){

    private lateinit var binding : FragmentLoginBinding

    lateinit var listener: Listener

    val bundle = Bundle()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater)

        binding.registerTxt.setOnClickListener {
            if(listener!=null){
                listener.onButtonClicked()
            }
        }

        binding.loginBtn.setOnClickListener {
            this@LoginFragment.findNavController().navigate(R.id.action_swipeLoginFragment_to_homeScreenFragment)
        }

        binding.otpBtn.setOnClickListener {
            bundle.putString(Constants.FROM_FRAGMENT,"LoginFragment")
            this@LoginFragment.findNavController().navigate(R.id.action_swipeLoginFragment_to_otpFragment,bundle)
        }

        binding.forgetPasswordTxt.setOnClickListener {
            bundle.putString(Constants.FROM_FRAGMENT,"LoginFragment")
            this@LoginFragment.findNavController().navigate(R.id.action_swipeLoginFragment_to_forgotPasswordFragment,bundle)
        }

        return binding.root
    }
}