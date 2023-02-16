package com.example.tutopiaapplication.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tutopiaapplication.R
import com.example.tutopiaapplication.databinding.FragmentForgotPasswordBinding
import com.example.tutopiaapplication.databinding.FragmentLoginBinding
import com.example.tutopiaapplication.databinding.FragmentRegisterBinding
import com.example.tutopiaapplication.databinding.TitleLayoutBinding

class ForgotPasswordFragment : Fragment() {

    private lateinit var binding : FragmentForgotPasswordBinding

    private lateinit var titleLayoutBinding: TitleLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentForgotPasswordBinding.inflate(inflater)

        titleLayoutBinding = binding.title

        titleLayoutBinding.headerTxt.visibility = View.GONE

        titleLayoutBinding.backImg.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }

        return binding.root
    }

}